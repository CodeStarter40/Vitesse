package com.openclassrooms.vitesse.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.openclassrooms.vitesse.R
import com.openclassrooms.vitesse.databinding.FragmentDetailcandidatBinding
import com.openclassrooms.vitesse.domain.model.Candidat
import com.openclassrooms.vitesse.ui.addedit.AddEditCandidatFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale


@AndroidEntryPoint
class DetailCandidatFragment : Fragment() {
    private lateinit var binding: FragmentDetailcandidatBinding
    private val viewModel: DetailCandidatViewModel by viewModels()
    private var candidatId: Long = -1




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailcandidatBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        arguments?.let {
            candidatId = it.getLong("candidatId")
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }

        if (candidatId != -1L) {
            viewModel.getCandidat(candidatId)
            viewModel.candidat.observe(viewLifecycleOwner) { candidat ->
                candidat?.let {
                    binding.toolbar.title = "${candidat.prenom} ${candidat.nom}"
                    loadPicture(candidat.picture)
                    /*binding.imageProfilCandidat.setImageResource(
                        resources.getIdentifier(candidat.picture, "drawable", requireContext().packageName)
                    )*/
                    binding.textViewDateNaissance.text = candidat.dateBirth

                    //salairePretend adapt
                    binding.salairePretend.text = "${candidat.pretend} €"

                    //back to CandidatFragment
                    binding.toolbar.setNavigationOnClickListener {
                        requireActivity().supportFragmentManager.popBackStack() }
                    //togglefavori clickListener
                    binding.favorite.setOnClickListener {
                        viewModel.candidat.value?.let { candidat -> viewModel.toggleFavori(candidat) }
                    }
                    //togglefavori check for adapt star icon
                    binding.favorite.setImageResource(
                        if (candidat.favori) R.drawable.icon_star_full else R.drawable.icon_star_empty)

                    //deleteCandidat on click trash icon and back to CandidatFragment
                    binding.delete.setOnClickListener {
                        showDeleteConfirmationDialog(candidat)
                    }
                    //verify if phonenumber is present and adapt the opacity icon and activ or desactiv the button
                    if (candidat.phone.isEmpty()) {
                        binding.iconCall.alpha = 0.2f
                        binding.iconCall.isEnabled = false
                        binding.iconSms.alpha = 0.2f
                        binding.iconSms.isEnabled = false
                    }

                    //verify if email is present and adapt the opacity icon and activ or desactiv the button
                    if (candidat.email.isEmpty()) {
                        binding.iconEmail.alpha = 0.2f
                        binding.iconEmail.isEnabled = false
                    }

                    //dialCandidat on click call icon
                    binding.iconCall.setOnClickListener {
                        dialCandidat(candidat.phone)
                    }
                    //sendSmsCandidat on click sms icon
                    binding.iconSms.setOnClickListener {
                        sendSmsCandidat(candidat.phone)
                    }
                    //sendMailToCandidat on click email icon
                    binding.iconEmail.setOnClickListener {
                        sendMailToCandidat(candidat.email)
                    }
                    //calcul age candidat
                    val age = calculateAge(candidat.dateBirth)
                    binding.ageaCalcule.text = "$age ans"

                    //sendCandidatToEditFragment on click edit icon
                    binding.edit.setOnClickListener {
                        sendCandidatToEditFragment(candidat)
                    }

                    /*
                    //binding of salairePretend in pound
                    binding.textViewSalaireLSCalcule.text = "soit £ ${convertPretendToPound(candidat.pretend)}"
                    */

                    //binding note adapt
                    binding.textNotes.text = candidat.note
                }
            }
        }
        //observe convertedAmount and adapt textview
        viewModel.convertedAmount.observe(viewLifecycleOwner) { formattedAmount ->
            binding.textViewSalaireLSCalcule.text = "soit £ $formattedAmount"}

        applyElevationEffect(binding.toolbar)
        applyElevationEffect(binding.delete)
        applyElevationEffect(binding.favorite)
        applyElevationEffect(binding.iconCall)
        applyElevationEffect(binding.iconSms)
        applyElevationEffect(binding.iconEmail)
        applyElevationEffect(binding.edit)
    }

    //load picture with glide
    private fun loadPicture(picture: String) {
        if (picture.startsWith("content://") || picture.startsWith("file://")) {
            Glide.with(this).load(Uri.parse(picture)).into(binding.imageProfilCandidat)
        }
        else {
            val imageResId = requireContext().resources.getIdentifier(picture, "drawable", requireContext().packageName)
            Glide.with(this).load(imageResId).into(binding.imageProfilCandidat)
        }
    }

    //elevation effect on click
    @SuppressLint("ClickableViewAccessibility")
    private fun applyElevationEffect(view: View) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.elevation = -8f
                    v.scaleX = 0.80f
                    v.scaleY = 0.80f
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    v.elevation = 8f
                    v.scaleX = 1f
                    v.scaleY = 1f
                }
            }
            false
        }
    }

    //showDeleteConfirmationDialog with confirmation toast message and delete
    private fun showDeleteConfirmationDialog(candidat: Candidat) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmer la suppression de ${candidat.prenom} ${candidat.nom} ?")
            .setMessage("Cette action est irreversible, est-ce que vous souhaitez continuer?")
            .setPositiveButton("Confirmer") { dialog,which ->
                viewModel.deleteCandidat(candidat)
                Toast.makeText(requireContext(), "Candidat supprimé", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
            .setNegativeButton("Annuler", null)
            .show()
    }

    //call candidat
    private fun dialCandidat(phone: String){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phone")
        startActivity(intent)
    }

    //sms to candidat
    private fun sendSmsCandidat(phone: String){
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto:$phone")
        startActivity(intent)
    }
    //send mail to candidat
    private fun sendMailToCandidat(email: String){
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:$email")
        startActivity(intent)
    }
    //calcul Candidat age with local date and dateofbirth
    private fun calculateAge(dateOfBirth: String): Int {
        val formattedDateOfBirth = dateOfBirth.trim()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.FRENCH)
        val birthDate = LocalDate.parse(formattedDateOfBirth, formatter)
        val currentDate = LocalDate.now()
        return Period.between(birthDate, currentDate).years
    }
    /*
    //convert pretend euro to pound
    private fun convertPretendToPound(pretend: Double): Double {
        return pretend * 0.85
    }
    */


    //send this candidat in to editfragment for update
    private fun sendCandidatToEditFragment(candidat: Candidat){
        val editFragment = AddEditCandidatFragment()
        val bundle = Bundle()
        Log.d("DETAILCANDIDATFRAGMENT", "sendCandidatToEditFragment called")
        bundle.putLong("candidatId", candidat.id)
        editFragment.arguments = bundle

        //starting transaction with fragment edit
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.Container_Fragment, editFragment)
            .addToBackStack(null)
            .commit()
    }
}
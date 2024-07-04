package com.openclassrooms.vitesse.ui.addedit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.vitesse.databinding.FragmentAddeditcandidatBinding
import com.openclassrooms.vitesse.domain.model.Candidat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditCandidatFragment : Fragment() {

    private var _binding: FragmentAddeditcandidatBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddEditCandidatViewModel by viewModels()
    private var candidatId: Long = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddeditcandidatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            Log.d("ADDEDITCANDIDAT", "BACK CALLED")}


        arguments?.let {
            candidatId = it.getLong("candidatId",-1)
        }

        if (candidatId != -1L) {
            viewModel.getCandidat(candidatId).observe(viewLifecycleOwner) { candidat ->
                candidat?.let {
                    preLoadFields(it)
                }
            }
        }

        binding.buttonSave.setOnClickListener {
            val prenom = binding.inputPrenom.text.toString()
            val nom = binding.inputNom.text.toString()
            val phone = binding.inputPhone.text.toString()
            val email = binding.inputEmail.text.toString()
            val dateBirth = binding.inputBirthDate.text.toString()
            val pretend = binding.inputSalarial.text.toString().toDouble()
            val note = binding.inputNote.text.toString()

            if (prenom.isNotEmpty() && nom.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && dateBirth.isNotEmpty() && pretend.toString().isNotEmpty() && note.isNotEmpty()) {
                val candidat = Candidat(
                    id = if (candidatId == -1L) 0 else candidatId,
                    prenom = prenom,
                    nom = nom,
                    phone = phone,
                    email = email,
                    dateBirth = dateBirth,
                    pretend = pretend,
                    note = note,
                    favori = false, //default false
                    picture = "male29" //default picture set for exemple, but u can set your function for taking photo
                )
                viewModel.saveCandidat(candidat) //use saveCandidat added on VModel
                //navigade back to fragmentCandidat and show success toast message
                requireActivity().supportFragmentManager.popBackStack()
                Toast.makeText(requireContext(), "Candidat ajouté", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //preLoadFields from candidat
    private fun preLoadFields(candidat: Candidat) {
        binding.inputPrenom.setText(candidat.prenom)
        binding.inputNom.setText(candidat.nom)
        binding.inputPhone.setText(candidat.phone)
        binding.inputEmail.setText(candidat.email)
        binding.inputBirthDate.setText(candidat.dateBirth)
        binding.inputSalarial.setText(candidat.pretend.toString())
        binding.inputNote.setText(candidat.note)
        binding.inputImageProfilCandidat.setImageResource(
            resources.getIdentifier(candidat.picture, "drawable", requireContext().packageName)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

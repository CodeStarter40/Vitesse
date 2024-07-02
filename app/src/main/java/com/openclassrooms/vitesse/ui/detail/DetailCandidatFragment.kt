package com.openclassrooms.vitesse.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.vitesse.R
import com.openclassrooms.vitesse.ui.candidat.DetailCandidatViewModel
import com.openclassrooms.vitesse.databinding.FragmentDetailcandidatBinding
import com.openclassrooms.vitesse.domain.model.Candidat
import dagger.hilt.android.AndroidEntryPoint


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        arguments?.let {
            candidatId = it.getLong("candidatId")
        }

        if (candidatId != -1L) {
            viewModel.getCandidat(candidatId)
            viewModel.candidat.observe(viewLifecycleOwner) { candidat ->
                candidat?.let {
                    binding.toolbar.title = "${candidat.prenom} ${candidat.nom}"
                    binding.imageProfilCandidat.setImageResource(
                        resources.getIdentifier(candidat.picture, "drawable", requireContext().packageName)
                    )
                    binding.textViewDateNaissance.text = candidat.dateBirth
                    binding.salairePretend.text = candidat.pretend.toString()
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
                }
            }
        }
    }
    //showDeleteConfirmationDialog with confirmation toast message and delete
    private fun showDeleteConfirmationDialog(candidat: Candidat) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmer la suppression de ${candidat.prenom} ${candidat.nom} ?")
            .setMessage("Cette action est irreversible, est-ce que vous souhaitez continuer?")
            .setPositiveButton("Oui") { dialog,which ->
                viewModel.deleteCandidat(candidat)
                Toast.makeText(requireContext(), "Candidat supprimé", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
            .setNegativeButton("Non", null)
            .show()
    }
}
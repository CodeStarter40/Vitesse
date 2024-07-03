package com.openclassrooms.vitesse.ui.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.vitesse.databinding.FragmentAddeditcandidatBinding
import com.openclassrooms.vitesse.databinding.FragmentCandidatsBinding
import com.openclassrooms.vitesse.domain.model.Candidat

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditCandidatFragment : Fragment() {

    private var _binding: FragmentAddeditcandidatBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddEditCandidatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddeditcandidatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSave.setOnClickListener {
            val prenom = binding.inputPrenom.text.toString()
            val nom = binding.inputNom.text.toString()
            val phone = binding.inputPhone.text.toString()
            val email = binding.inputEmail.text.toString()
            val dateBirth = binding.inputBirthDate.text.toString()
            val pretend = binding.inputSalarial.text.toString().toDouble()
            val note = binding.inputNote.text.toString()

            if (prenom.isNotEmpty() && nom.isNotEmpty()) {
                val candidat = Candidat(
                    id = 0,
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
                viewModel.saveCandidat(candidat) //saveCandidat added on VModel
                //navigade back to fragmentCandidat and show success toast message
                requireActivity().supportFragmentManager.popBackStack()
                Toast.makeText(requireContext(), "Candidat ajouté", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

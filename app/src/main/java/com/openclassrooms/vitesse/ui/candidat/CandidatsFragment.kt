package com.openclassrooms.vitesse.ui.candidat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.vitesse.R
import com.openclassrooms.vitesse.databinding.FragmentCandidatsBinding
import com.openclassrooms.vitesse.domain.model.Candidat
import com.openclassrooms.vitesse.ui.addedit.AddEditCandidatFragment
import com.openclassrooms.vitesse.ui.detail.DetailCandidatFragment
import com.openclassrooms.vitesse.ui.homefragment.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CandidatsFragment : Fragment() {

    private lateinit var binding: FragmentCandidatsBinding
    //init viewModel using viewModels delegate
    private val viewModel: CandidatsViewModel by viewModels()
    private lateinit var adapter: CandidatAdapter
    private var allCandidats: List<Candidat> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("CANDIDATSFRAGMENT", "onCreateView called")
        //binding for inflating the xmlLayout of this fragment
        binding = FragmentCandidatsBinding.inflate(inflater, container, false)
        //return the root view of the binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("CANDIDATSFRAGMENT", "onViewCreated called")

        //init the adapter for the recyclerview
        adapter = CandidatAdapter { candidat ->
            val bundle = Bundle().apply {
                putLong("candidatId", candidat.id)
            }
            val fragment = DetailCandidatFragment()
            fragment.arguments = bundle
            (activity as MainActivity).loadFragment(fragment)
        }

        //init the fab button
        val fab : FloatingActionButton = binding.fab
        fab.setOnClickListener {
            val fragment = AddEditCandidatFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.Container_Fragment, fragment)
                ?.addToBackStack(null)
                ?.commit()
        }

        //set the layoutManager for the recyclerview
        binding.candidatRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.candidatRecyclerview.adapter = adapter

        //observe livedata from viewmodel and submit the list to the adapter
        viewModel.candidats.observe(viewLifecycleOwner, Observer { candidats ->
            Log.d("CANDIDATSFRAGMENT", "Observed candidats: ${candidats.size}")
            allCandidats = candidats
            adapter.submitList(candidats)
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        //init the searchview
        setupSearchView()
    }


    private fun setupSearchView() {
        val searchView = requireActivity().findViewById<SearchView>(R.id.search_View)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterCandidats(query ?: "")
                //hide the keyboard
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterCandidats(newText ?: "")
                return true
            }
        })
    }

    //filter the list of candidats based on the query
    private fun filterCandidats(query: String) {
        val filteredList = if (query.isNotEmpty()) {
            allCandidats.filter {
                it.nom.contains(query, ignoreCase = true) || it.prenom.contains(query, ignoreCase = true)
            }
        } else {
            allCandidats
        }
        adapter.submitList(filteredList) //update the adapter with the filtered list
        checkEmptyList(filteredList)
    }

    //check if the list is empty and show a message to the user
    private fun checkEmptyList(list: List<Candidat>) {
        binding.noResultsCandidats.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }
}

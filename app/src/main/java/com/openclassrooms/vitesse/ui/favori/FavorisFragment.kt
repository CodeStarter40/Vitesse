package com.openclassrooms.vitesse.ui.favori

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
import com.openclassrooms.vitesse.R
import com.openclassrooms.vitesse.databinding.FragmentFavorisBinding
import com.openclassrooms.vitesse.domain.model.Candidat
import com.openclassrooms.vitesse.ui.detail.DetailCandidatFragment
import com.openclassrooms.vitesse.ui.homefragment.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavorisFragment:Fragment() {

    private lateinit var binding: FragmentFavorisBinding
    private lateinit var adapter: FavorisAdapter
    //init viewModel
    private val viewModel: FavorisViewModel by viewModels()
    private var allFavoris: List<Candidat> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("FAVORISFRAGMENT", "onCreateView called")
        //use binding for inflating the xmlLayout of this fragment
        binding = FragmentFavorisBinding.inflate(inflater, container, false)
        //return the root view of the binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FAVORISFRAGMENT", "onViewCreated called")

        //init the adapter for the recyclerview
        adapter = FavorisAdapter { candidat ->
            val bundle = Bundle().apply {
                putLong("candidatId", candidat.id)
            }
            val fragment = DetailCandidatFragment()
            fragment.arguments = bundle
            (activity as MainActivity).loadFragment(fragment)
        }

        //set the layoutManager for the recyclerview
        binding.favorisRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.favorisRecyclerview.adapter = adapter

        //observe livedata from viewmodel and submit the list to the adapter
        viewModel.favoris.observe(viewLifecycleOwner, Observer { favoris ->
            Log.d("FAVORISFRAGMENT", "Observed favoris: ${favoris.size}")
            if (favoris.isEmpty()) {
                Log.d("FAVORISFRAGMENT", "No favoris on display")
            } else {
                Log.d("FAVORISFRAGMENT", "Favoris on display: ${favoris.size}")
            }
            adapter.submitList(favoris)
            allFavoris = favoris
            checkEmptyList(favoris)
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
        setupSearchView()
    }

    //setup SearchView for favoris filter
    private fun setupSearchView() {
        val searchView = requireActivity().findViewById<SearchView>(R.id.search_View)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterFavoris(query ?: "")
                //hide the keyboard
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterFavoris(newText ?: "")
                return true
            }
        })
    }

    //filter the list of favoris based on the query
    private fun filterFavoris(query: String) {
        val filteredList = if (query.isNotEmpty()) {
            allFavoris.filter {
                it.nom.contains(query, ignoreCase = true) || it.prenom.contains(query, ignoreCase = true)
            }
        } else {
            allFavoris
        }
        adapter.submitList(filteredList) //update the adapter with the filtered list
        checkEmptyList(filteredList)
    }

    private fun checkEmptyList(list: List<Candidat>) {
        binding.noResultsFavoris.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }
}

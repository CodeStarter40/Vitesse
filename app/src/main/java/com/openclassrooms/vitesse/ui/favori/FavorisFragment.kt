package com.openclassrooms.vitesse.ui.favori

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.vitesse.databinding.FragmentFavorisBinding
import com.openclassrooms.vitesse.ui.candidat.FavorisAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavorisFragment:Fragment() {

    private lateinit var binding: FragmentFavorisBinding
    //init viewModel
    private val viewModel: FavorisViewModel by viewModels()

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
        val adapter = FavorisAdapter()

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
            })
    }
}

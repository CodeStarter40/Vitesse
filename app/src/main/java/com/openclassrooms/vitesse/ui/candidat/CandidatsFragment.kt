package com.openclassrooms.vitesse.ui.candidat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.vitesse.databinding.FragmentCandidatsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CandidatsFragment : Fragment(){


    private lateinit var binding: FragmentCandidatsBinding
    //init viewModel using viewModels delegate
    private val viewModel: CandidatsViewModel by viewModels()

    override  fun onCreateView(
        inflater: LayoutInflater,container:ViewGroup?,savedInstanceState: Bundle?
    ): View {
        Log.d("CANDIDATSFRAGMENT", "onCreateView called")
        //use binding for inflating the xmlLayout of this fragment
        binding = FragmentCandidatsBinding.inflate(inflater,container,false)
        //return the root view of the layout
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("CANDIDATSFRAGMENT", "onViewCreated called")

        //init the adapter for the recyclerview
        val adapter = CandidatAdapter()

        //set the layoutmanager  and adapter for the recyclerview
        binding.candidatRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.candidatRecyclerview.adapter = adapter

        //observe the livedata from viewModel and submit the list to the adapter
        viewModel.candidats.observe(viewLifecycleOwner, Observer { candidats ->
            //if the list is not null, submit it to the adapter
            candidats?.let { adapter.submitList(candidats) } })
    }

}
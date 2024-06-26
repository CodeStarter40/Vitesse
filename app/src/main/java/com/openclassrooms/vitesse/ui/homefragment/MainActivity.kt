package com.openclassrooms.vitesse.ui.homefragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.openclassrooms.vitesse.R
import com.openclassrooms.vitesse.data.database.AppDatabase
import com.openclassrooms.vitesse.databinding.ActivityMainBinding
import com.openclassrooms.vitesse.ui.candidat.CandidatsFragment
import com.openclassrooms.vitesse.ui.candidat.CandidatsViewModel
import com.openclassrooms.vitesse.ui.favori.FavorisFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.openclassrooms.vitesse.ui.detail.DetailCandidatFragment

@AndroidEntryPoint
class  MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CandidatsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //exec fun setupTabLayout
        setupTabLayout()

        //add observer of fragment change
        supportFragmentManager.addOnBackStackChangedListener {
            Log.d("MAINACTIVITY", "Fragment changed")
            handleFragmentChanges()
        }

        Log.d("MAINACTIVITY", "onCreate called")

        val db = AppDatabase.getDatabase(this, lifecycleScope)
        //init the database with the activity scope
        lifecycleScope.launch { getCandidat(db) }


        viewModel.candidats.observe(this) { candidats ->
            Log.d("MAINACTIVITY", "Candidats observed: ${candidats.size}") }

    }

    private fun setupTabLayout() {
        val tabLayout = binding.tabLayout

        //setup tablayout
        val tabAll = tabLayout.newTab().apply { text = "TOUS" }
        val tabFavoris = tabLayout.newTab().apply { text = "FAVORIS" }

        //add tabs
        tabLayout.addTab(tabAll)
        tabLayout.addTab(tabFavoris)

        //set Listener
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.d("MAINACTIVITY", "Tab selected: ${tab.position}")
                when (tab.position) {
                    //bind pos to fragment
                    0 -> loadFragment(CandidatsFragment())
                    1 -> loadFragment(FavorisFragment())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                //WIP
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                //WIP
            }
        })

        //set initial fragment (allcandidats)
        loadFragment(CandidatsFragment())
    }

    fun loadFragment(fragment: Fragment) {
        Log.d("MAINACTIVITY", "fun loadFragment called on fragment: ${fragment.javaClass.simpleName}")
        supportFragmentManager.beginTransaction()
            //gate to find error xD
            .replace(R.id.Container_Fragment, fragment)
            .addToBackStack(null) //add transaction to backstack
            //exec asynch transaction
            .commit()
        //update the visibility based on fragmenttype (hide search and tab)
        if (fragment is DetailCandidatFragment) {
            hideSearchView()
        } else {
            showSearchAndTab()
        }
    }

    private fun handleFragmentChanges() {
        val fragment = supportFragmentManager.findFragmentById(R.id.Container_Fragment)
        if (fragment is CandidatsFragment || fragment is FavorisFragment) {
            showSearchAndTab()
        } else {
            hideSearchView()
        }
    }

    private fun hideSearchView() {
        binding.searchView.visibility = View.GONE
        binding.tabLayout.visibility = View.GONE
    }

    private fun showSearchAndTab() {
        binding.searchView.visibility = View.VISIBLE
        binding.tabLayout.visibility = View.VISIBLE
    }

    private suspend fun getCandidat(db: AppDatabase) {
        db.candidatDao().getAllCandidat().collect {
            Log.d("MAINACTIVITY", "fun getCandidat called")
        }
    }


}

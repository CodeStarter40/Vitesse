package com.openclassrooms.vitesse.ui.homefragment

import android.os.Bundle
import android.util.Log
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

@AndroidEntryPoint
class  MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CandidatsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTabLayout()

        Log.d("MAINACTIVITY", "onCreate called")

        val db = AppDatabase.getDatabase(this, lifecycleScope)
        //init the database with the activity scope
        lifecycleScope.launch { getCandidat(db) }


        viewModel.candidats.observe(this) { candidats ->
            Log.d("MAINACTIVITY", "Candidats observed: ${candidats.size}") }

    }

    private fun setupTabLayout() {
        val tabLayout = binding.tabLayout

        //add tabs
        val tabAll = tabLayout.newTab().apply { text = "TOUS" }
        val tabFavoris = tabLayout.newTab().apply { text = "FAVORIS" }

        tabLayout.addTab(tabAll)
        tabLayout.addTab(tabFavoris)

        //set Listener
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> loadFragment(CandidatsFragment())
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

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.Container_Fragment, fragment)
            .commit()
    }

    suspend fun getCandidat(db: AppDatabase) {
        db.candidatDao().getAllCandidat().collect {
            Log.d("MAINACTIVITY", "getCandidat called")
        }
    }
}

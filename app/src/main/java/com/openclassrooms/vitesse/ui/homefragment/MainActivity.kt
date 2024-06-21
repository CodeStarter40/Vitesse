package com.openclassrooms.vitesse.ui.homefragment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.openclassrooms.vitesse.R
import com.openclassrooms.vitesse.data.database.AppDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.android.car.ui.toolbar.TabLayout
import com.openclassrooms.vitesse.databinding.ActivityMainBinding
import com.openclassrooms.vitesse.ui.candidat.CandidatsFragment
import com.openclassrooms.vitesse.ui.candidat.CandidatsViewModel
import com.openclassrooms.vitesse.ui.favori.FavorisFragment

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CandidatsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupTablLayout()

        Log.d("MAINACTIVITY", "onCreate called")

        val db = AppDatabase.getDatabase(this, lifecycleScope)
        //Init the database with the activity scope

        GlobalScope.launch { getCandidat(db) }
    }

    private fun setupTablLayout() {
        val tabLayout = binding.tabLayout

        //add tabs

        val tabAll = tabLayout.newTab().apply { text = "Tous" }
        val tabFavoris = tabLayout.newTab().apply { text = "Favoris" }

        tabLayout.addTab(tabAll)
        tabLayout.addTab(tabFavoris)


        //set Listnener
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> loadFragment(CandidatsFragment)
                    1 -> loadFragment(FavorisFragment)
                }
            }
        })
        //set initial fragment
        loadFragment(CandidatsFragment)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.Container_Fragment, fragment)
            .commit()
    }


    suspend fun getCandidat(db: AppDatabase){
        db.candidatDao().getAllCandidat().collect{
            Log.d("MAINACTIVITY", "getCandidat called")
        }
    }
}

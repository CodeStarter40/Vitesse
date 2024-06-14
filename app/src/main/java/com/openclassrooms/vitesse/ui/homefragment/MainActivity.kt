package com.openclassrooms.vitesse.ui.homefragment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.openclassrooms.vitesse.R
import com.openclassrooms.vitesse.data.database.AppDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MAINACTIVITY", "onCreate called")

        val db = AppDatabase.getDatabase(this, lifecycleScope)
        //Init the database with the activity scope
        GlobalScope.launch { getCandidat(db) }
    }
    private suspend fun getCandidat(db: AppDatabase){
        db.candidatDao().getAllCandidat().collect{
            Log.d("MAINACTIVITY", "getCandidat called")
        }
    }
}

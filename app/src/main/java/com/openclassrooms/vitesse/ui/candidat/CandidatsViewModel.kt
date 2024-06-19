package com.openclassrooms.vitesse.ui.candidat

import android.media.CamcorderProfile.getAll
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.vitesse.data.database.AppDatabase
import com.openclassrooms.vitesse.domain.model.Candidat
import kotlinx.coroutines.launch


class CandidatsViewModel @ViewModelInject constructor(private val db: AppDatabase) : ViewModel() {

    private val _candidats = MutableLiveData<List<Candidat>>()
    val candidats: LiveData<List<Candidat>>
        get() = _candidats

    init {
        loadCandidats()
    }

    private fun loadCandidats() {
        viewModelScope.launch {
            db.candidatDao().getAllCandidat().collect {
            }
        }
    }
}
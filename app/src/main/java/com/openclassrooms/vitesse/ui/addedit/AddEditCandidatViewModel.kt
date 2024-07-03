package com.openclassrooms.vitesse.ui.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.vitesse.data.database.AppDatabase
import com.openclassrooms.vitesse.domain.model.Candidat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditCandidatViewModel @Inject constructor(private val db: AppDatabase) : ViewModel() {

    //add function to save candidat
    fun saveCandidat(candidat: Candidat) {
        viewModelScope.launch {
            db.candidatDao().insertCandidat(candidat.toDto())
        }
    }


    //add function to update candidat
    fun updateCandidat(candidat: Candidat) {
        viewModelScope.launch {
            db.candidatDao().updateCandidat(candidat.toDto())
        }
    }
}

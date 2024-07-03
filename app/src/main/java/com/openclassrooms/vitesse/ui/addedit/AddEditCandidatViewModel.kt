package com.openclassrooms.vitesse.ui.addedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.vitesse.data.database.AppDatabase
import com.openclassrooms.vitesse.domain.model.Candidat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditCandidatViewModel @Inject constructor(private val db: AppDatabase) : ViewModel() {

    private val _candidat = MutableLiveData<Candidat?>()
    val candidat: LiveData<Candidat?>
        get() = _candidat

    //add function to save candidat
    fun saveCandidat(candidat: Candidat) {
        viewModelScope.launch(Dispatchers.IO) {
            //pickup candidat from db with  candidat.id
            val existingCandidat = db.candidatDao().getCandidatById(candidat.id)

            //verification if the candidat already exists
            if (existingCandidat != null) {
                //update candidat
                db.candidatDao().updateCandidat(candidat.toDto())
            } else {
                //if not existe insert candidat in db
                db.candidatDao().insertCandidat(candidat.toDto())
            }
        }
    }

    fun getCandidat(id: Long): MutableLiveData<Candidat?> {
        viewModelScope.launch(Dispatchers.IO) {
            val candidatDto = db.candidatDao().getCandidatById(id)
            val candidat = candidatDto.let { Candidat.fromDto(it) }
            _candidat.postValue(candidat)
        }
        return _candidat
    }


    //add function to update candidat
    fun updateCandidat(candidat: Candidat) {
        viewModelScope.launch {
            db.candidatDao().updateCandidat(candidat.toDto())
        }
    }
}

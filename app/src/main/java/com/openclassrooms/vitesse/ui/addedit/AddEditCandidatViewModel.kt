package com.openclassrooms.vitesse.ui.addedit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.vitesse.data.database.AppDatabase
import com.openclassrooms.vitesse.data.repository.CandidatsRepository
import com.openclassrooms.vitesse.domain.model.Candidat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditCandidatViewModel @Inject constructor(private val candidatRepository: CandidatsRepository) : ViewModel() {

    private val _candidat = MutableLiveData<Candidat?>()
    val candidat: LiveData<Candidat?>
        get() = _candidat

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    //add function to save candidat
    fun saveCandidat(candidat: Candidat) {
        viewModelScope.launch(Dispatchers.IO) {
            //pickup candidat from db with  candidat.id
            val existingCandidat = candidatRepository.getCandidatById(candidat.id)

            //verification if the candidat already exists
            if (existingCandidat != null) {
                //update candidat
                candidatRepository.updateCandidat(candidat)
            } else {
                //if not existe insert candidat in db
                candidatRepository.insertCandidat(candidat)
            }
        }
    }
    //get candidat by id
    fun getCandidat(id: Long): MutableLiveData<Candidat?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //use repository to get candidat
                val candidatDto = candidatRepository.getCandidatById(id)
                //check if candidatDto is not null
                if (candidatDto != null) {
                    //convert candidatDto to candidat
                    val candidat = Candidat.fromDto(candidatDto)
                    _candidat.postValue(candidat)
                } else {
                    _candidat.postValue(null)
                    _errorMessage.postValue("Candidat non trouvé")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Erreur lors de la récupération du candidat : ${e.message}")
                Log.e("DETAILCANDIDATVIEWMODEL", "Erreur lors de la récupération du candidat : ${e.message}")
                }
        }
        return _candidat
    }

    /*
    //add function to update candidat
    fun updateCandidat(candidat: Candidat) {
        viewModelScope.launch {
            db.candidatDao().updateCandidat(candidat.toDto())
        }
    }

     */
}

package com.openclassrooms.vitesse.ui.detail

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.vitesse.data.database.AppDatabase
import com.openclassrooms.vitesse.data.repository.CurrencyRepository
import com.openclassrooms.vitesse.domain.model.Candidat
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCandidatViewModel @Inject constructor(private val db: AppDatabase, private val repository: CurrencyRepository) : ViewModel() {


    private val _candidat = MutableLiveData<Candidat?>()
    val candidat: LiveData<Candidat?>
        get() = _candidat

    private val _convertedAmount = MutableLiveData<Double>()
    val convertedAmount: LiveData<Double> get() = _convertedAmount

    fun getCandidat(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val candidatDto = db.candidatDao().getCandidatById(id)
                val candidat = candidatDto?.let { Candidat.fromDto(it) }
                _candidat.postValue(candidat)
                //convert salary to pounds if candidat is not null
                candidat?.let {
                    Log.d("DETAILCANDIDATVIEWMODEL", "Fetching conversion rate")
                    val rate = repository.convertEurosToPounds()
                    Log.d("DETAILCANDIDATVIEWMODEL", "Conversion rate fetched: $rate")
                    val convertedAmount = it.pretend * rate
                    _convertedAmount.postValue(convertedAmount)
                    //conversion format with 2 decimal places, replace "," with "."
                    val formattedAmount = String.format("%.2f", convertedAmount).replace(",", ".").toDouble()
                    _convertedAmount.postValue(formattedAmount)
                    Log.d("DETAILCANDIDATVIEWMODEL", "convertSalaryToPounds called with amount: ${it.pretend}")
                }
            } catch (e: Exception) {
                Log.e("DETAILCANDIDATVIEWMODEL", "Error fetching candidat or converting currency: ${e.message}")
            }
        }
    }

    fun toggleFavori(candidat: Candidat) {
        viewModelScope.launch(Dispatchers.IO) {
            candidat.favori = !candidat.favori
            db.candidatDao().updateCandidat(candidat.toDto())
            Log.d("DETAILCANDIDATVIEWMODEL", "toggleFavori UPDATE CANDIDAT")
            _candidat.postValue(candidat)
            Log.d("DETAILCANDIDATVIEWMODEL", "toggleFavori COMPLETED")
        }
    }

    fun deleteCandidat(candidat: Candidat) {
        viewModelScope.launch(Dispatchers.IO) {
            db.candidatDao().deleteCandidat(candidat.toDto())
            Log.d("DETAILCANDIDATVIEWMODEL", "deleteCandidat DELETE CANDIDAT")
            _candidat.postValue(null)
            Log.d("DETAILCANDIDATVIEWMODEL", "deleteCandidat COMPLETED")
        }
    }

    fun modifyCandidat(candidat: Candidat) {
        //WIP
    }
}

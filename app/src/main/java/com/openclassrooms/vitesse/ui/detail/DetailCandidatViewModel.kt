package com.openclassrooms.vitesse.ui.candidat

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
class DetailCandidatViewModel @Inject constructor(private val db: AppDatabase) : ViewModel() {

    private val _candidat = MutableLiveData<Candidat?>()
    val candidat: LiveData<Candidat?>
        get() = _candidat

    fun getCandidat(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val candidatDto = db.candidatDao().getCandidatById(id)
            val candidat = candidatDto.let { Candidat.fromDto(it) }
            _candidat.postValue(candidat)
        }
    }
}

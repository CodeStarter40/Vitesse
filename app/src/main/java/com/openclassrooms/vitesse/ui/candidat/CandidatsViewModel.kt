package com.openclassrooms.vitesse.ui.candidat

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CandidatsViewModel @Inject constructor(private val candidatRepository: CandidatsRepository) : ViewModel() {

    private val _candidats = MutableLiveData<List<Candidat>>()
    val candidats: LiveData<List<Candidat>>
        get() = _candidats


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading


    init {
        loadCandidats()
    }

    private fun loadCandidats() {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            candidatRepository.getAllCandidats().collect { candidatsDto ->
                val candidats = candidatsDto.map { Candidat.fromDto(it) }
                _candidats.postValue(candidats)
                _loading.postValue(false)
                Log.d("CANDIDATSVIEWMODEL", "Loaded Candidats: ${candidats.size}")
            }
        }
    }
}
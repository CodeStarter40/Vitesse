package com.openclassrooms.vitesse.ui.favori

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
class FavorisViewModel @Inject constructor(private val candidatRepository: CandidatsRepository) : ViewModel() {

    private val _favoris = MutableLiveData<List<Candidat>>()
    val favoris: LiveData<List<Candidat>>
        get() = _favoris


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    init {
        loadFavoris()
    }

    private fun loadFavoris() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            delay(300)
            candidatRepository.getFavoris().collect { candidatsDto ->
                val favoris = candidatsDto.map { Candidat.fromDto(it) }
                _favoris.postValue(favoris)
                _loading.postValue(false)
                Log.d("FAVORISVIEWMODEL", "Loaded Favoris: ${favoris.size}")
            }
        }
    }
}
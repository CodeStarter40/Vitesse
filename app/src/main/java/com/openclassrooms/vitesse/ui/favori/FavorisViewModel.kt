package com.openclassrooms.vitesse.ui.favori

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.vitesse.data.database.AppDatabase
import com.openclassrooms.vitesse.domain.model.Candidat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavorisViewModel @Inject constructor(private val db: AppDatabase) : ViewModel() {
    private val _favoris = MutableLiveData<List<Candidat>>()
    val favoris: LiveData<List<Candidat>>
        get() = _favoris

    init {
        loadFavoris()
    }

    private fun loadFavoris() {
        viewModelScope.launch {
            db.candidatDao().getFavoris().collect { candidatsDto ->
                val favoris = candidatsDto.map { Candidat.fromDto(it) }
                _favoris.postValue(favoris)
                Log.d("FAVORISVIEWMODEL", "Loaded Favoris: ${favoris.size}")
            }
        }
    }
}
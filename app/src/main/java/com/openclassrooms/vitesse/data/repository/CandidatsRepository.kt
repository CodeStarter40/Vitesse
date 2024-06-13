package com.openclassrooms.vitesse.data.repository


import com.openclassrooms.vitesse.data.dao.CandidatDtoDao
import com.openclassrooms.vitesse.data.entity.CandidatDto
import com.openclassrooms.vitesse.domain.model.Candidat
import kotlinx.coroutines.flow.Flow

class CandidatsRepository(private val candidatDao: CandidatDtoDao) {
    fun getAllCandidats(): Flow<List<CandidatDto>> = candidatDao.getAllCandidat()


    //add candidat
    suspend fun addCandidat(candidat: Candidat) {
        candidatDao.insertCandidat(candidat.toDto())
    }

}
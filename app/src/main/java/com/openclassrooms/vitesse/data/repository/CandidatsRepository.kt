package com.openclassrooms.vitesse.data.repository


import com.openclassrooms.vitesse.data.dao.CandidatDtoDao
import com.openclassrooms.vitesse.data.entity.CandidatDto
import com.openclassrooms.vitesse.domain.model.Candidat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CandidatsRepository @Inject constructor(private val candidatDao: CandidatDtoDao) {
    fun getAllCandidats(): Flow<List<CandidatDto>> {
        return candidatDao.getAllCandidat()
    }

    //add candidat
    suspend fun addCandidat(candidat: Candidat) {
        candidatDao.insertCandidat(candidat.toDto())
    }

    //delete candidatbyId
    suspend fun deleteCandidatById(id: Long){
        candidatDao.deleteCandidatById(id)
    }
}
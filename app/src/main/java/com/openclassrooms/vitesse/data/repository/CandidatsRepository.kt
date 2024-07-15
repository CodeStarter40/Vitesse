package com.openclassrooms.vitesse.data.repository


import com.openclassrooms.vitesse.data.dao.CandidatDtoDao
import com.openclassrooms.vitesse.data.entity.CandidatDto
import com.openclassrooms.vitesse.domain.model.Candidat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CandidatsRepository @Inject constructor(private val candidatDao: CandidatDtoDao) {
    //get all candidat
    fun getAllCandidats(): Flow<List<CandidatDto>> {
        return candidatDao.getAllCandidat()
    }

    //delete candidatbyId
    suspend fun deleteCandidatById(id: Long){
        candidatDao.deleteCandidatById(id)
    }

    //update candidat
    suspend fun updateCandidat(candidat: Candidat) {
        candidatDao.updateCandidat(candidat.toDto())
    }

    //get candidat by id
    fun getCandidatById(id: Long): CandidatDto {
        return candidatDao.getCandidatById(id)
    }

    //insert candidat
    suspend fun insertCandidat(candidat: Candidat) {
        candidatDao.insertCandidat(candidat.toDto())
    }

    //get favoris
    fun getFavoris(): Flow<List<CandidatDto>> {
        return candidatDao.getFavoris()
    }

}
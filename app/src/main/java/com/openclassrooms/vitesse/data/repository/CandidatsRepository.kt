package com.openclassrooms.vitesse.data.repository


import com.openclassrooms.vitesse.data.dao.CandidatDtoDao
import com.openclassrooms.vitesse.data.entity.CandidatDto
import kotlinx.coroutines.flow.Flow

class CandidatsRepository(private val candidatDao: CandidatDtoDao) {
    val allCandidats: Flow<List<CandidatDto>> = candidatDao.getAllCandidat()
}
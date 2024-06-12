package com.openclassrooms.vitesse.data.repository

import com.openclassrooms.vitesse.data.entity.CandidatDto
import com.openclassrooms.vitesse.data.dao.CandidatDtoDao
import kotlinx.coroutines.flow.Flow

class CandidatsRepository(private val candidatDtoDao: CandidatDtoDao) {
    val allCandidats: Flow<List<CandidatDto>> = candidatDtoDao.getAllCandidat()
}
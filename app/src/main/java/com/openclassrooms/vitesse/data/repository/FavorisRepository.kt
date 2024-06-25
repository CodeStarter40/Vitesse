package com.openclassrooms.vitesse.data.repository

import com.openclassrooms.vitesse.data.dao.CandidatDtoDao
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FavorisRepository @Inject constructor(private val candidatDao: CandidatDtoDao) {

}
package com.openclassrooms.vitesse.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.openclassrooms.vitesse.data.dao.CandidatDtoDao
import com.openclassrooms.vitesse.data.entity.CandidatDto

@Database(entities = [CandidatDto::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun candidatDao(): CandidatDtoDao
}
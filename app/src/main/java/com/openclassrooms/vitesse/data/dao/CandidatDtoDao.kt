package com.openclassrooms.vitesse.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.openclassrooms.vitesse.data.entity.CandidatDto
import kotlinx.coroutines.flow.Flow


@Dao
interface CandidatDtoDao {
    @Query("SELECT * FROM candidat")
    fun getAllCandidat(): Flow<List<CandidatDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCandidat(candidat: CandidatDto)

    @Update
    suspend fun updateCandidat(candidat: CandidatDto)

    @Delete
    suspend fun deleteCandidat(candidat: CandidatDto)

    @Query("DELETE FROM candidat WHERE id = :candidatId")
    suspend fun deleteCandidatById(candidatId: Int)



}
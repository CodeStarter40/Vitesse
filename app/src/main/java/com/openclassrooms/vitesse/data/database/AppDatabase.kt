package com.openclassrooms.vitesse.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.vitesse.data.dao.CandidatDtoDao
import com.openclassrooms.vitesse.data.entity.CandidatDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [CandidatDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun candidatDao(): CandidatDtoDao

    private class AppDataBaseCallBack(private val scope: CoroutineScope):Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database -> scope.launch {
                    populateDatabase(database.candidatDao())
                }
            }
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase?=null //instance de la base de données init null


        fun getDatabase(context: Context, coroutineScope: CoroutineScope):AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "VitesseDataBase"
                )
                    .addCallback(AppDataBaseCallBack(coroutineScope))
                    .build()
                INSTANCE=instance
                instance
            }
        }
        suspend fun populateDatabase(candidatDao: CandidatDtoDao) {
            candidatDao.insertCandidat(
            CandidatDto(
                id = 1,
                nom = "Macke",
                prenom = "Ben",
                phone = "1234567890",
                email = "ben.macke@test.com",
                picture = "url_picture_1",
                dateBirth = "1986-2-15",
                pretend = 50000.0,
                favori = false,
                note = "New 1",
                )
            )
        }
    }
}
package com.openclassrooms.vitesse.data.database

import android.content.Context
import android.util.Log
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
            Log.d("APPDATABASECALLBACK", "onCreate appel")
            INSTANCE?.let { database ->
                Log.d("APPDATABASECALLBACK", "instance not null, launch coroutine")
                scope.launch {
                    Log.d("APPDATABASECALLBACK", "in courout scope, call populateDatabase")
                    populateDatabase(database.candidatDao())
                }
            }
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase?=null //instance de la base de données init null


        fun getDatabase(context: Context, coroutineScope: CoroutineScope):AppDatabase {
            Log.d("APPDATABASE", "getDatabase called")
            return INSTANCE ?: synchronized(this){
                Log.d("APPDATABASE", "create new instance of database")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "VitesseDataBase"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDataBaseCallBack(coroutineScope))
                    .build()
                Log.d("APPDATABASE", "database builded and callback added")
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

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 2,
                    nom = "Jacke",
                    prenom = "Ben",
                    phone = "1232343214",
                    email = "ben.jacke@test2.com",
                    picture = "url_picture_2",
                    dateBirth = "1986-4-15",
                    pretend = 60000.0,
                    favori = true,
                    note = "New 2",
                )
            )

            Log.d("POPULATEDATABASE", "populateDatabase called and ended")
        }
    }
}
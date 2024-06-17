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

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 3,
                    nom = "Dupont",
                    prenom = "Jean",
                    phone = "3214321432",
                    email = "jean.dupont@test3.com",
                    picture = "url_picture_3",
                    dateBirth = "1990-3-22",
                    pretend = 55000.0,
                    favori = false,
                    note = "New 3",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 4,
                    nom = "Martin",
                    prenom = "Sophie",
                    phone = "2342342345",
                    email = "sophie.martin@test4.com",
                    picture = "url_picture_4",
                    dateBirth = "1987-7-12",
                    pretend = 62000.0,
                    favori = true,
                    note = "New 4",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 5,
                    nom = "Bernard",
                    prenom = "Pierre",
                    phone = "5436546543",
                    email = "pierre.bernard@test5.com",
                    picture = "url_picture_5",
                    dateBirth = "1992-1-20",
                    pretend = 58000.0,
                    favori = false,
                    note = "New 5",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 6,
                    nom = "Dubois",
                    prenom = "Marie",
                    phone = "7654321098",
                    email = "marie.dubois@test6.com",
                    picture = "url_picture_6",
                    dateBirth = "1985-11-30",
                    pretend = 67000.0,
                    favori = true,
                    note = "New 6",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 7,
                    nom = "Lefevre",
                    prenom = "Luc",
                    phone = "9087654321",
                    email = "luc.lefevre@test7.com",
                    picture = "url_picture_7",
                    dateBirth = "1983-2-18",
                    pretend = 63000.0,
                    favori = false,
                    note = "New 7",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 8,
                    nom = "Moreau",
                    prenom = "Julie",
                    phone = "8765432109",
                    email = "julie.moreau@test8.com",
                    picture = "url_picture_8",
                    dateBirth = "1989-9-25",
                    pretend = 61000.0,
                    favori = true,
                    note = "New 8",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 9,
                    nom = "Laurent",
                    prenom = "Paul",
                    phone = "6547890123",
                    email = "paul.laurent@test9.com",
                    picture = "url_picture_9",
                    dateBirth = "1991-5-14",
                    pretend = 59000.0,
                    favori = false,
                    note = "New 9",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 10,
                    nom = "Simon",
                    prenom = "Laura",
                    phone = "0987654321",
                    email = "laura.simon@test10.com",
                    picture = "url_picture_10",
                    dateBirth = "1984-12-10",
                    pretend = 65000.0,
                    favori = true,
                    note = "New 10",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 11,
                    nom = "Michel",
                    prenom = "Hugo",
                    phone = "3210987654",
                    email = "hugo.michel@test11.com",
                    picture = "url_picture_11",
                    dateBirth = "1988-6-5",
                    pretend = 64000.0,
                    favori = false,
                    note = "New 11",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 12,
                    nom = "Leroy",
                    prenom = "Claire",
                    phone = "1234567890",
                    email = "claire.leroy@test12.com",
                    picture = "url_picture_12",
                    dateBirth = "1993-8-27",
                    pretend = 66000.0,
                    favori = true,
                    note = "New 12",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 13,
                    nom = "Roux",
                    prenom = "Antoine",
                    phone = "6789012345",
                    email = "antoine.roux@test13.com",
                    picture = "url_picture_13",
                    dateBirth = "1986-10-19",
                    pretend = 67000.0,
                    favori = false,
                    note = "New 13",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 14,
                    nom = "Girard",
                    prenom = "Camille",
                    phone = "2345678901",
                    email = "camille.girard@test14.com",
                    picture = "url_picture_14",
                    dateBirth = "1982-4-17",
                    pretend = 64000.0,
                    favori = true,
                    note = "New 14",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 15,
                    nom = "Gauthier",
                    prenom = "Louis",
                    phone = "3456789012",
                    email = "louis.gauthier@test15.com",
                    picture = "url_picture_15",
                    dateBirth = "1987-3-8",
                    pretend = 63000.0,
                    favori = false,
                    note = "New 15",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 16,
                    nom = "Perrin",
                    prenom = "Alice",
                    phone = "4567890123",
                    email = "alice.perrin@test16.com",
                    picture = "url_picture_16",
                    dateBirth = "1990-7-13",
                    pretend = 61000.0,
                    favori = true,
                    note = "New 16",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 17,
                    nom = "Chevalier",
                    prenom = "Thomas",
                    phone = "5678901234",
                    email = "thomas.chevalier@test17.com",
                    picture = "url_picture_17",
                    dateBirth = "1983-1-2",
                    pretend = 60000.0,
                    favori = false,
                    note = "New 17",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 18,
                    nom = "Blanc",
                    prenom = "Elodie",
                    phone = "6789012345",
                    email = "elodie.blanc@test18.com",
                    picture = "url_picture_18",
                    dateBirth = "1989-9-9",
                    pretend = 62000.0,
                    favori = true,
                    note = "New 18",
                )
            )


            Log.d("POPULATEDATABASE", "populateDatabase called and ended")
        }
    }
}
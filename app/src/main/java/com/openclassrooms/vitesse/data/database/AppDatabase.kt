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
                    email = "",
                    picture = "@drawable/male71",
                    dateBirth = "1986-02-15",
                    pretend = 50000.0,
                    favori = false,
                    note = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 2,
                    nom = "Jacke",
                    prenom = "Ben",
                    phone = "",
                    email = "ben.jacke@test2.com",
                    picture = "@drawable/male67",
                    dateBirth = "1986-04-15",
                    pretend = 60000.0,
                    favori = true,
                    note = "Nunc dolor quam, placerat et laoreet vitae, porta nec elit. Aenean vitae erat rhoncus, interdum mi ac, sodales odio. Suspendisse tincidunt dignissim lacus ut pretium. Morbi a nisi vehicula, tempus nulla at, varius justo. Mauris consequat nisl vitae erat gravida, sed aliquam ligula iaculis. Sed a ante dolor.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 3,
                    nom = "Dupont",
                    prenom = "Jean",
                    phone = "3214321432",
                    email = "jean.dupont@test3.com",
                    picture = "@drawable/male46",
                    dateBirth = "1990-03-22",
                    pretend = 55000.0,
                    favori = false,
                    note = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 4,
                    nom = "Martin",
                    prenom = "Sophie",
                    phone = "2342342345",
                    email = "sophie.martin@test4.com",
                    picture = "@drawable/female90",
                    dateBirth = "1987-07-12",
                    pretend = 62000.0,
                    favori = false,
                    note = "Aenean a lectus sed est fringilla mollis. Nam accumsan justo vel lacus pretium rutrum. Suspendisse lobortis dolor bibendum pharetra imperdiet. Ut aliquet augue congue odio pretium faucibus. Sed vulputate aliquam sem, sed vehicula odio tincidunt at. Nunc id vestibulum magna. Aenean quis sodales sapien.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 5,
                    nom = "Bernard",
                    prenom = "Pierre",
                    phone = "5436546543",
                    email = "pierre.bernard@test5.com",
                    picture = "@drawable/male34",
                    dateBirth = "1992-01-20",
                    pretend = 58000.0,
                    favori = false,
                    note = "Donec ut nisl volutpat, interdum risus non, rutrum leo. Pellentesque nunc purus, bibendum in nisl ut, tempus euismod est. Integer augue mi, viverra sit amet ultrices eget, malesuada eget ipsum.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 6,
                    nom = "Dubois",
                    prenom = "Marie",
                    phone = "7654321098",
                    email = "marie.dubois@test6.com",
                    picture = "@drawable/female73",
                    dateBirth = "1985-11-30",
                    pretend = 67000.0,
                    favori = true,
                    note = "Sed nibh justo, mollis vel dapibus id, mollis non nunc. Fusce consectetur justo quis luctus malesuada. Donec pretium viverra varius. Pellentesque eget nunc urna.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 7,
                    nom = "Lefevre",
                    prenom = "Luc",
                    phone = "9087654321",
                    email = "luc.lefevre@test7.com",
                    picture = "@drawable/male75",
                    dateBirth = "1983-02-18",
                    pretend = 63000.0,
                    favori = false,
                    note = "Etiam semper eros egestas, faucibus lacus in, tempus ipsum. Cras aliquam vulputate ipsum, ut suscipit ipsum rhoncus et. Nam pulvinar leo vitae massa malesuada, quis dictum dui blandit. Ut nec lorem mi. Praesent in felis convallis, consequat nunc vel, dignissim sapien.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 8,
                    nom = "Moreau",
                    prenom = "Julie",
                    phone = "8765432109",
                    email = "julie.moreau@test8.com",
                    picture = "@drawable/female54",
                    dateBirth = "1989-09-25",
                    pretend = 61000.0,
                    favori = true,
                    note = "Vivamus elit mi, feugiat ut gravida at, convallis ut nibh. Cras quis elit ut eros mattis dapibus. Cras a rutrum augue. Integer felis sem, lacinia in turpis sit amet, ornare molestie dui.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 9,
                    nom = "Laurent",
                    prenom = "Paul",
                    phone = "6547890123",
                    email = "paul.laurent@test9.com",
                    picture = "@drawable/male73",
                    dateBirth = "1991-05-14",
                    pretend = 59000.0,
                    favori = false,
                    note = "Cras aliquam vulputate ipsum, ut suscipit ipsum rhoncus et. Nam pulvinar leo vitae massa malesuada, quis dictum dui blandit. Ut nec lorem mi. Praesent in felis convallis, consequat nunc vel, dignissim sapien. Vivamus elit mi, feugiat ut gravida at, convallis ut nibh.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 10,
                    nom = "Simon",
                    prenom = "Laura",
                    phone = "0987654321",
                    email = "laura.simon@test10.com",
                    picture = "@drawable/female23",
                    dateBirth = "1984-12-10",
                    pretend = 65000.0,
                    favori = true,
                    note = "Donec mauris est, mattis tempus pretium at, varius vel orci. Fusce finibus augue non purus tempor, ac pharetra lorem vulputate. Nunc sit amet quam quam. Donec a consequat nisl.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 11,
                    nom = "Michel",
                    prenom = "Hugo",
                    phone = "3210987654",
                    email = "hugo.michel@test11.com",
                    picture = "@drawable/male72",
                    dateBirth = "1988-06-05",
                    pretend = 64000.0,
                    favori = false,
                    note = "Quisque facilisis, quam vel commodo posuere, libero neque efficitur felis, et egestas libero erat vitae ex. Ut id elit vel nisi vulputate vehicula.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 12,
                    nom = "Leroy",
                    prenom = "Claire",
                    phone = "1234567890",
                    email = "claire.leroy@test12.com",
                    picture = "@drawable/female72",
                    dateBirth = "1993-08-27",
                    pretend = 66000.0,
                    favori = true,
                    note = "Integer fringilla enim nisi, sit amet varius ipsum blandit a. Etiam sit amet turpis tristique, pharetra felis eu, fringilla ex.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 13,
                    nom = "Roux",
                    prenom = "Antoine",
                    phone = "6789012345",
                    email = "antoine.roux@test13.com",
                    picture = "@drawable/male70",
                    dateBirth = "1986-10-19",
                    pretend = 67000.0,
                    favori = false,
                    note = "Suspendisse dictum blandit finibus. Cras in auctor nulla, ut mollis sapien. Aliquam et enim sapien. Ut faucibus neque mollis urna porta euismod. ",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 14,
                    nom = "Girard",
                    prenom = "Camille",
                    phone = "2345678901",
                    email = "camille.girard@test14.com",
                    picture = "@drawable/female45",
                    dateBirth = "1982-04-17",
                    pretend = 64000.0,
                    favori = true,
                    note = "Curabitur vel commodo sem. Etiam fringilla, enim in mollis pellentesque, eros ligula egestas tortor, eu pellentesque purus tortor vel velit.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 15,
                    nom = "Gauthier",
                    prenom = "Louis",
                    phone = "3456789012",
                    email = "louis.gauthier@test15.com",
                    picture = "@drawable/male42",
                    dateBirth = "1987-03-08",
                    pretend = 63000.0,
                    favori = false,
                    note = "Pellentesque nunc purus, bibendum in nisl ut, tempus euismod est. Integer augue mi, viverra sit amet ultrices eget, malesuada eget ipsum. Curabitur vel commodo sem. Etiam fringilla, enim in mollis pellentesque, eros ligula egestas tortor, eu pellentesque purus tortor vel velit.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 16,
                    nom = "Perrin",
                    prenom = "Alice",
                    phone = "4567890123",
                    email = "alice.perrin@test16.com",
                    picture = "@drawable/female44",
                    dateBirth = "1990-07-13",
                    pretend = 61000.0,
                    favori = true,
                    note = "Morbi tincidunt, massa auctor ultricies pulvinar, nulla ante gravida sapien, at consequat libero mauris eget turpis.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 17,
                    nom = "Chevalier",
                    prenom = "Thomas",
                    phone = "5678901234",
                    email = "thomas.chevalier@test17.com",
                    picture = "@drawable/male29",
                    dateBirth = "1983-01-02",
                    pretend = 60000.0,
                    favori = false,
                    note = "Quisque elementum sodales tempus. Vivamus quam nisl, mattis vitae facilisis sit amet, facilisis quis diam. In est felis, faucibus a sodales vitae, cursus id lorem.",
                )
            )

            candidatDao.insertCandidat(
                CandidatDto(
                    id = 18,
                    nom = "Blanc",
                    prenom = "Elodie",
                    phone = "6789012345",
                    email = "elodie.blanc@test18.com",
                    picture = "@drawable/female42",
                    dateBirth = "1989-09-09",
                    pretend = 62000.0,
                    favori = true,
                    note = "Suspendisse lobortis dolor bibendum pharetra imperdiet. Ut aliquet augue congue odio pretium faucibus. Sed vulputate aliquam sem, sed vehicula odio tincidunt at. Nunc id vestibulum magna.",
                )
            )


            Log.d("POPULATEDATABASE", "populateDatabase called and ended")
        }
    }
}
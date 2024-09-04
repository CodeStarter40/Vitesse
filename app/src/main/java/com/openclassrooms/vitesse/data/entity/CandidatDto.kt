package com.openclassrooms.vitesse.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "candidat")
data class CandidatDto (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "nom")
    var nom: String,

    @ColumnInfo(name = "prenom")
    var prenom: String,

    @ColumnInfo(name = "dateBirth")
    var dateBirth: String,

    @ColumnInfo(name = "picture")
    var picture: String,

    @ColumnInfo(name = "email")
    var email:String,

    @ColumnInfo(name = "note")
    var note:String,

    @ColumnInfo(name = "pretend")
    var pretend:Double,

    @ColumnInfo(name = "favori")
    var favori:Boolean,

    @ColumnInfo(name = "phone")
    var phone:String,

)
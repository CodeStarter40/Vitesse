package com.openclassrooms.vitesse.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.security.KeyStore.TrustedCertificateEntry
import java.util.Date

@Entity(tableName = "candidat")
data class CandidatDto (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "nom")
    val nom: String,

    @ColumnInfo(name = "prenom")
    val prenom: String,

    @ColumnInfo(name = "dateBirth")
    val dateBirth: String,

    @ColumnInfo(name = "picture")
    val picture: String,

    @ColumnInfo(name = "email")
    val email:String,

    @ColumnInfo(name = "note")
    val note:String,

    @ColumnInfo(name = "pretend")
    val pretend:Double,

    @ColumnInfo(name = "favori")
    val favori:Boolean,

    @ColumnInfo(name = "phone")
    val phone:String,

)


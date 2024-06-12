package com.openclassrooms.vitesse.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.security.KeyStore.TrustedCertificateEntry
import java.util.Date

@Entity(tableName = "candidat")
data class CandidatDto (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val nom: String,
    val prenom: String,
    val dateBirth: Date,
    val photo: String,
    val email:String,
    val note:String,
    val pretend:Double,
    val favori:Boolean,
    val phone:String,

)


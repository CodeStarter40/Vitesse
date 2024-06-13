package com.openclassrooms.vitesse.domain.model

import java.util.Date

data class Candidat(

    val id: Int,
    val nom: String,
    val prenom: String,
    val phone: String,
    val email: String,
    val picture: String,
    val dateBirth: String,
    val pretend:Double,
    val favori: Boolean,
    val note:String
)



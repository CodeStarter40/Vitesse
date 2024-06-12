package com.openclassrooms.vitesse.domain.model

import org.w3c.dom.Text
import java.util.Date

data class Candidat(

    val id: Int,
    val nom: String,
    val prenom: String,
    val phone: String,
    val email: String,
    val picture: String,
    val dateBirth: Date,
    val pretend:Double,
    val favori: Boolean,
    val note:String
)



package com.openclassrooms.vitesse.domain.model

import com.openclassrooms.vitesse.data.entity.CandidatDto
import java.util.Date

data class Candidat(@JvmField

    val id: Long,
    var nom: String,
    var prenom: String,
    var phone: String,
    var email: String,
    var picture: String,
    var dateBirth: String,
    var pretend:Double,
    var favori: Boolean,
    var note:String
){
    companion object
    fun fromDto(dto: CandidatDto): Candidat {
        return Candidat(
            id = dto.id,
            nom = dto.nom,
            prenom = dto.prenom,
            phone = dto.phone,
            email = dto.email,
            picture = dto.picture,
            dateBirth = dto.dateBirth,
            pretend = dto.pretend,
            favori = dto.favori,
            note = dto.note
        )
    }
    fun toDto():CandidatDto {
        return CandidatDto(
            id = this.id,
            nom = nom,
            prenom = prenom,
            phone = phone,
            email = email,
            picture = picture,
            dateBirth = dateBirth,
            pretend = pretend,
            favori = favori,
            note = note
        )
    }
}




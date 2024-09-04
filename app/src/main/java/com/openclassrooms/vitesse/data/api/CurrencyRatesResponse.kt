package com.openclassrooms.vitesse.data.api

//data class to get currency rates
data class CurrencyRatesResponse(
    val date: String,
    val eur: Map<String, Double>
)

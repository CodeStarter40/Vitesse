package com.openclassrooms.vitesse.data.api

import retrofit2.Response
import retrofit2.http.GET


interface CurrencyApiService {
    //https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/"eur.json"
    @GET("eur.json")
    //funtion to get currency rates
    suspend fun getCurrencyRates(): Response<CurrencyRatesResponse>
}


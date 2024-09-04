package com.openclassrooms.vitesse.data.repository

import android.util.Log
import com.openclassrooms.vitesse.data.api.CurrencyApiService
import com.openclassrooms.vitesse.data.api.CurrencyRatesResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor(private val api: CurrencyApiService) {


    suspend fun convertEurosToPounds(): Double {
        val response: Response<CurrencyRatesResponse>
        try {
            //call api for catch change rates
            Log.d("CURRENCYREPOSITORY", "Calling API")
            response = api.getCurrencyRates()
            //verif http response code
            Log.d("CURRENCYREPOSITORY", "HTTP Response Code: ${response.code()}")
            Log.d("CURRENCYREPOSITORY", "HTTP Response Body: ${response.body().toString()}")
            //if 200
            if (response.isSuccessful) {
                //extract rates change on "rates" val
                val rates = response.body()?.eur
                //check if rates contains "gbp" key and not null
                if (rates != null && rates.containsKey("gbp")) {
                    Log.d("CURRENCYREPOSITORY", "Conversion rate found for GBP")
                    //return rate value
                    return rates["gbp"] ?: 0.0
                } else {
                    //if not found, exeption
                    throw Exception("Conversion rate not found for GBP")
                }
            } else {
                //exception if API call failed with r.code
                throw Exception("API call failed with response code: ${response.code()}")
            }
        } catch (e: Exception) {
            //exception with other message
            throw Exception("API call failed: ${e.message}")
        }
    }
}
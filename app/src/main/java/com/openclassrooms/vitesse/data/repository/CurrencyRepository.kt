package com.openclassrooms.vitesse.data.repository

import android.util.Log
import com.openclassrooms.vitesse.data.api.CurrencyApiService
import com.openclassrooms.vitesse.data.api.CurrencyRatesResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor() { private val api: CurrencyApiService


    //init retrofit
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        Log.d("CURRENCYREPOSITORY", "init called")

        api=retrofit.create(CurrencyApiService::class.java)
    }
    //
    suspend fun convertEurosToPounds(): Double {
        val response: Response<CurrencyRatesResponse>
        try {
            //call api for catch change rates
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
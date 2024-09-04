package com.openclassrooms.vitesse.di

import android.content.Context
import com.openclassrooms.vitesse.data.api.CurrencyApiService
import com.openclassrooms.vitesse.data.dao.CandidatDtoDao
import com.openclassrooms.vitesse.data.database.AppDatabase
import com.openclassrooms.vitesse.data.repository.CandidatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.Default)
    }

    //fournit une instance de la base de données
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context, coroutineScope: CoroutineScope):AppDatabase {
        return AppDatabase.getDatabase(context, coroutineScope)
    }

    //fournit le DAO
    @Provides
    fun provideCandidatDao(database: AppDatabase): CandidatDtoDao {
        return database.candidatDao()
    }


    //fournit une instance du repository
    @Provides
    @Singleton
    fun provideCandidatsRepository(candidatDao: CandidatDtoDao): CandidatsRepository {
        return CandidatsRepository(candidatDao)
    }

    //fournit retrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    //fournit CurrencyApiService
    @Provides
    @Singleton
    fun provideCurrencyApiService(retrofit: Retrofit): CurrencyApiService {
        return retrofit.create(CurrencyApiService::class.java)
    }


}
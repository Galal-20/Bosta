package com.galal.bosta.data.api

import dagger.Module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
object ApiClient {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://stg-app.bosta.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val searchApi: SearchApi by lazy {
        retrofit.create(SearchApi::class.java)
    }
}
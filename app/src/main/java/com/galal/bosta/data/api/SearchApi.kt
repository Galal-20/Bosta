package com.galal.bosta.data.api

import com.galal.bosta.model.Search
import retrofit2.http.GET

interface SearchApi {

    @GET("cities/getAllDistricts?countryId=60e4482c7cb7d4bc4849c4d5")
    suspend fun getAllDistricts(): Search

}
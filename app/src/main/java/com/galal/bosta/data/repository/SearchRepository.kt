package com.galal.bosta.data.repository

import com.galal.bosta.data.api.ApiState
import com.galal.bosta.model.Search

interface SearchRepository {
    suspend fun fetchDistricts(): ApiState<Search>


}
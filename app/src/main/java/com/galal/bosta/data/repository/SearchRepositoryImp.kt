package com.galal.bosta.data.repository

import com.galal.bosta.data.api.ApiState
import com.galal.bosta.data.api.SearchApi
import com.galal.bosta.model.Search

class SearchRepositoryImp(
    private val api: SearchApi
) : SearchRepository {

    override suspend fun fetchDistricts(): ApiState<Search> {
        return try {
            val response = api.getAllDistricts()
            ApiState.Success(response)
        } catch (e: Exception) {
            ApiState.Failure(e.localizedMessage ?: "Unknown error")
        }
    }
}
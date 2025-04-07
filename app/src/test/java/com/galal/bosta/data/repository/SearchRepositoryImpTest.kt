package com.galal.bosta.data.repository

import com.galal.bosta.data.api.ApiState
import com.galal.bosta.data.api.SearchApi
import com.galal.bosta.model.Search
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class SearchRepositoryImpTest{

  private lateinit var searchRepository: SearchRepositoryImp
 private val mockApi: SearchApi = mockk()


 @Before
 fun setUp(){
   searchRepository = SearchRepositoryImp(mockApi)
 }

 @Test
 fun `fetchDistricts should return success when API call is successful`() = runTest {
   val expectedResponse = mockk<Search>()
   coEvery { mockApi.getAllDistricts() } returns expectedResponse
   val result = searchRepository.fetchDistricts()
   assert(result is ApiState.Success)
   assert((result as ApiState.Success).data == expectedResponse)
 }


    @Test
    fun `fetchDistricts should return failure when API call fails`() = runTest {
        val errorMessage = "API call failed"
        coEvery { mockApi.getAllDistricts() } throws Exception(errorMessage)
        val result = searchRepository.fetchDistricts()
        assert(result is ApiState.Failure)
        assert((result as ApiState.Failure).message == errorMessage)

    }


 }
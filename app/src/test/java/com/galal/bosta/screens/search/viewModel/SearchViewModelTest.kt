package com.galal.bosta.screens.search.viewModel

import com.galal.bosta.data.api.ApiState
import com.galal.bosta.data.repository.SearchRepository
import com.galal.bosta.model.Search
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchViewModelTest {

 private lateinit var viewModel: SearchViewModel
 private val mockRepository: SearchRepository = mockk()
 private val testDispatcher = UnconfinedTestDispatcher()

 @Before
 fun setUp() {
  Dispatchers.setMain(testDispatcher)
 }

 @After
 fun tearDown() {
  Dispatchers.resetMain()
 }

 @Test
 fun `fetchDistricts should update districtsState when successful`() = runTest {
  val mockResponse = mockk<ApiState<Search>>()
  coEvery { mockRepository.fetchDistricts() } returns mockResponse

  viewModel = SearchViewModel(mockRepository, autoFetch = false)
  viewModel.fetchDistricts()

  assertEquals(mockResponse, viewModel.districtsState.value)
  coVerify { mockRepository.fetchDistricts() }
 }

 @Test
 fun `fetchDistricts should update districtsState when failure`() = runTest {
  val errorMessage = "Error searching for districts"
  val failureState = ApiState.Failure(errorMessage)
  coEvery { mockRepository.fetchDistricts() } returns failureState

  viewModel = SearchViewModel(mockRepository, autoFetch = false)
  viewModel.fetchDistricts()

  val state = viewModel.districtsState.value
  assert(state is ApiState.Failure)
  assertEquals(errorMessage, (state as ApiState.Failure).message)
  coVerify { mockRepository.fetchDistricts() }
 }

 @Test
 fun `updateSearchQuery filters the data correctly`() {
  viewModel = SearchViewModel(mockRepository, autoFetch = false)

  val fullData = mapOf(
   "Cairo" to listOf("Nasr City" to false, "Heliopolis" to true),
   "Alexandria" to listOf("Smouha" to false)
  )

  viewModel.apply {
   val fullDataField = this::class.java.getDeclaredField("fullData")
   fullDataField.isAccessible = true
   fullDataField.set(this, fullData)
   updateSearchQuery("Cairo")
  }

  assertEquals(mapOf("Cairo" to listOf("Nasr City" to false, "Heliopolis" to true)), viewModel.filteredData.value)
 }
}
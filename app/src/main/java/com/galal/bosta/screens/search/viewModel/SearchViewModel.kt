package com.galal.bosta.screens.search.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galal.bosta.data.api.ApiState
import com.galal.bosta.data.repository.SearchRepository
import com.galal.bosta.model.Search
import kotlinx.coroutines.launch


class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    private val _districtsState = mutableStateOf<ApiState<Search>>(ApiState.Loading)
    val districtsState: State<ApiState<Search>> = _districtsState

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _filteredData = mutableStateOf<Map<String, List<Pair<String, Boolean>>>>(emptyMap())
    val filteredData: State<Map<String, List<Pair<String, Boolean>>>> = _filteredData

    private var fullData: Map<String, List<Pair<String, Boolean>>> = emptyMap()

    init {
        fetchDistricts()
    }

    fun fetchDistricts() {
        viewModelScope.launch {
            _districtsState.value = ApiState.Loading
            when (val result = repository.fetchDistricts()) {
                is ApiState.Success -> {
                    val cityAreaData = result.data.data.associate { datum ->
                        datum.cityName to datum.districts.map { district ->
                            district.districtName to !district.pickupAvailability
                        }
                    }
                    fullData = cityAreaData
                    _filteredData.value = cityAreaData
                    _districtsState.value = result
                }
                else -> {
                    _districtsState.value = result
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        _filteredData.value = fullData.filterKeys {
            it.contains(query, ignoreCase = true) ||
                    fullData[it]?.any { area -> area.first.contains(query, ignoreCase = true) } == true
        }
    }
}

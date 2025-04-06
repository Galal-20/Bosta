package com.galal.bosta.screens.search.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.galal.bosta.data.api.ApiState
import com.galal.bosta.screens.search.viewModel.SearchViewModel
import com.galal.bosta.utils.AppBar
import com.galal.bosta.utils.LoadingIndicator



@Composable
fun DeliveryAreaScreen(
    viewModel: SearchViewModel,
    onClose: () -> Unit
) {
    val searchQuery by viewModel.searchQuery
    val filteredCities by viewModel.filteredData
    val state by viewModel.districtsState
    val expandedCity = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 16.dp, end = 16.dp, top = 50.dp)
    ) {
        AppBar(onClose)
        Spacer(modifier = Modifier.height(8.dp))

        SearchBar(
            searchQuery = searchQuery,
            onSearchChanged = { viewModel.updateSearchQuery(it) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        when (state) {
            is ApiState.Loading -> {
                LoadingIndicator()
            }

            is ApiState.Failure -> {
                Text("Failed to load data", color = Color.Red)
            }

            is ApiState.Success -> {
                LazyColumn {
                    filteredCities.forEach { (city, areas) ->
                        item {
                            CityItem(
                                city = city,
                                isExpanded = expandedCity.value == city,
                                onExpandToggle = {
                                    expandedCity.value = if (expandedCity.value == city) null else city
                                }
                            )
                        }

                        if (expandedCity.value == city) {
                            items(areas) { (areaName, isUncovered) ->
                                AreaItem(area = areaName, uncovered = isUncovered)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(searchQuery: String, onSearchChanged: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(12.dp),
            value = searchQuery,
            onValueChange = onSearchChanged,
            placeholder = { Text("City/Area") },
            trailingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Gray
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun CityItem(city: String, isExpanded: Boolean, onExpandToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpandToggle() }
            .padding(vertical = 12.dp, horizontal = 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = city, fontSize = 16.sp)
        Icon(
            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default
                .KeyboardArrowDown,
            contentDescription = null
        )
    }
    Divider()
}


@Composable
fun AreaItem(area: String, uncovered: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray.copy(alpha = 0.2f))
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = area, fontSize = 15.sp)
        if (uncovered) {
            Text(
                text = "Uncovered",
                fontSize = 12.sp,
                color = Gray,
                modifier = Modifier
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}


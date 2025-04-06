package com.galal.bosta.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.galal.bosta.data.api.ApiClient
import com.galal.bosta.data.repository.SearchRepositoryImp
import com.galal.bosta.ui.theme.BostaTheme
import com.galal.bosta.screens.search.view.DeliveryAreaScreen
import com.galal.bosta.screens.search.viewModel.SearchViewModel
import com.galal.bosta.screens.search.viewModel.SearchViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(SearchRepositoryImp(ApiClient.searchApi))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BostaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    DeliveryAreaScreen(viewModel, onClose = {it})
                }
            }
        }
    }
}



package com.oztasibrahimomer.chatapp.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel= hiltViewModel()
) {

    val channels = viewModel.channels.collectAsState()

    Scaffold {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {
            if (channels.value.isEmpty()) {
                Text("No channels available")
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize()
                ) {
                    items(channels.value) { channel ->
                        Column {
                            Text(text = channel.data, color = Color.Blue)
                        }
                    }
                }
            }
        }
    }
}

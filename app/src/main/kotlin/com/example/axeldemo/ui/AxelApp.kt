package com.example.axeldemo.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.axeldemo.ui.theme.AxelDemoTheme
import com.example.ui.search.SearchScreenView
import com.example.ui.search.viewmodel.SearchViewModel

@Composable
fun AxelApp(viewModel: SearchViewModel) {
    AxelDemoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            SearchScreenView(viewModel)
        }
    }
}

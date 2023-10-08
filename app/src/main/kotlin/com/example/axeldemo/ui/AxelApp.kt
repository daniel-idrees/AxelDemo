package com.example.axeldemo.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.axeldemo.ui.nav.MainNavHost
import com.example.axeldemo.ui.theme.AxelDemoTheme

@Composable
fun AxelApp() {
    AxelDemoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            MainNavHost()
        }
    }
}

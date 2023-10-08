package com.example.axeldemo.ui.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.ui.detail.DetailScreenView
import com.example.ui.detail.navigation.detailScreen
import com.example.ui.detail.navigation.navigateToDetail
import com.example.ui.nav.SearchScreen
import com.example.ui.search.SearchScreenView
import com.example.ui.search.navigation.searchScreen

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SearchScreen.route,
    ) {
        detailScreen {
            DetailScreenView(
                viewModel = hiltViewModel(),
            ) { navController.popBackStack(SearchScreen.route, false) }
        }

        searchScreen {
            SearchScreenView(
                viewModel = hiltViewModel(),
                navigateToDetail = { searchQuery -> navController.navigateToDetail(searchQuery) },
            )
        }
    }
}

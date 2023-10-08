package com.example.ui.search.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.ui.nav.SearchScreen

fun NavGraphBuilder.searchScreen(
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) = composable(SearchScreen.route, content = content)

fun NavController.navigateToSearch() {
    this.navigate(
        SearchScreen.route,
        navOptions {
            this.launchSingleTop = true
        },
    )
}

package com.example.ui.detail.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.ui.nav.DetailScreen
import com.example.ui.nav.detailScreenArgumentUserNameKey

fun NavGraphBuilder.detailScreen(
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) = composable(DetailScreen.route, content = content)

fun NavController.navigateToDetail(userName: String) {
    this.navigate(
        DetailScreen.routeTo(userName),
        navOptions {
            this.launchSingleTop = true
        },
    )
}

private fun DetailScreen.routeTo(userName: String) =
    this.route.replace("{$detailScreenArgumentUserNameKey}", userName)

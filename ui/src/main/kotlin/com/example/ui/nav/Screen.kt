package com.example.ui.nav

sealed class Screen(val route: String)
object DetailScreen : Screen("detail/{$detailScreenArgumentUserNameKey}")
object SearchScreen : Screen("search")

const val detailScreenArgumentUserNameKey = "userName"

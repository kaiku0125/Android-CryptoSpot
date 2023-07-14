package com.kaiku.cryptospot.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    path: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {
//    val route: String = path.appendArguments(arguments)
//
//    object One : Screen("one")
//    object Two : Screen("two")
//    object Four : Screen("four", listOf(
//        navArgument("user") {
//            type = NavUserType()
//            nullable = false
//        }
//    )) {
//        const val ARG = "user"
//        fun createRoute(user: User): String {
//            return route.replace("{${arguments.first().name}}", user.toString())
//        }
//    }
//
//    object Three : Screen("three",
//        listOf(navArgument("channelId") { type = NavType.StringType })) {
//        const val ARG = "channelId"
//        fun createRoute(str: String): String {
//            return route.replace("{${arguments.first().name}}", str)
//        }
//    }
}
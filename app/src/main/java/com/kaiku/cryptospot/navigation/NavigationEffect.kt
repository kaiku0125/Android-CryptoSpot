package com.kaiku.cryptospot.navigation

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import timber.log.Timber
import com.kaiku.cryptospot.extension.handleComposeNavigationIntent
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("RestrictedApi")
@Composable
fun NavigationEffect(
    startDestination: String,
    isAnimated: Boolean = true,
    builder: NavGraphBuilder.() -> Unit,
) {
    val navController = if (isAnimated) {
        rememberNavController()
    } else {
        rememberNavController()
    }
    val activity = (LocalContext.current as? Activity)
    val flow = NavChannel.navChannel

    LaunchedEffect(activity, navController, flow) {
        flow.collect {
            if (activity?.isFinishing == true) {
                return@collect
            }
            navController.handleComposeNavigationIntent(it)

        }
    }

    LaunchedEffect(Unit) {
        navController.currentBackStack.collectLatest {
            it.forEachIndexed { index, navBackStackEntry ->
                Timber.d("【NavStack】 index = $index, screen = ${navBackStackEntry.destination.route}")
            }
        }

    }

    if (isAnimated) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            builder = builder
        )

    } else {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            builder = builder
        )

    }
}




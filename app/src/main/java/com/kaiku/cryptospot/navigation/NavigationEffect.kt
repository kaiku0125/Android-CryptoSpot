package com.kaiku.cryptospot.navigation

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import timber.log.Timber
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.kaiku.cryptospot.common.Debug
import com.kaiku.cryptospot.extension.handleComposeNavigationIntent

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationEffect(
    startDestination: String,
    isAnimated: Boolean = true,
    builder: NavGraphBuilder.() -> Unit,
) {
    val navController = if (isAnimated) {
        rememberAnimatedNavController()
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
            navController.backQueue.forEachIndexed { index, navBackStackEntry ->
                if (Debug.isNAv) Timber.e("【NavStack】 index = $index, screen = ${navBackStackEntry.destination.route}")
            }
        }
    }

    if (isAnimated) {
        AnimatedNavHost(
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




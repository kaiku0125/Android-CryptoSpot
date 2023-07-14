package com.kaiku.cryptospot.navigation

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.rememberNavController
import timber.log.Timber
import androidx.navigation.compose.NavHost as NavHost


@Composable
fun NavigationEffect(
    startDestination: String, builder: NavGraphBuilder.() -> Unit,
) {
    val navController = rememberNavController()
    val activity = (LocalContext.current as? Activity)
    val flow = NavChannel.navChannel
    LaunchedEffect(activity, navController, flow) {
        flow.collect {
            if (activity?.isFinishing == true) {
                return@collect
            }
            navController.handleComposeNavigationIntent(it)
            navController.backQueue.forEachIndexed { index, navBackStackEntry ->
                Timber.e("index:$index=NavigationEffects: ${navBackStackEntry.destination.route}",)
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = startDestination,
        builder = builder
    )
}

fun NavController.handleComposeNavigationIntent(intent: NavIntent) {
    when (intent) {
        is NavIntent.Back -> {
            if (intent.route != null) {
                popBackStack(intent.route, intent.inclusive)
            } else {
                currentBackStackEntry?.destination?.route?.let {
                    popBackStack()
                }
            }
        }
        is NavIntent.To -> {
            navigate(intent.route) {
                launchSingleTop = intent.isSingleTop
                intent.popUpToRoute?.let { popUpToRoute ->
                    popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                }
            }
        }
        is NavIntent.Replace -> {
            navigate(intent.route) {
                launchSingleTop = intent.isSingleTop
                currentBackStackEntry?.destination?.route?.let {
                    popBackStack()
                }
            }
        }

        is NavIntent.OffAllTo -> navigate(intent.route) {
            popUpTo(0)
        }
    }
}




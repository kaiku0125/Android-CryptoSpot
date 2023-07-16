package com.kaiku.cryptospot.extension

import androidx.navigation.NavController
import com.kaiku.cryptospot.navigation.data.NavIntent

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
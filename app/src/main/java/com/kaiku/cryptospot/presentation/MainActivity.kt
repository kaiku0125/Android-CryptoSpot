package com.kaiku.cryptospot.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.kaiku.cryptospot.BuildConfig
import com.kaiku.cryptospot.MultiTagTree
import com.kaiku.cryptospot.navigation.NavRoute
import com.kaiku.cryptospot.presentation.home.HomeScreen
import com.kaiku.cryptospot.presentation.theme.CryptoSpotTheme
import com.kaiku.cryptospot.ui.theme.LoginView
import com.kaiku.cryptospot.view.CryptoListScreen
import dagger.hilt.android.AndroidEntryPoint
import org.amobile.mqtt_k.prefs.Prefs
import timber.log.Timber

//adb connect 127.0.0.1:62001

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG)
            Timber.plant(MultiTagTree())
        Prefs.load(this)

        setContent {
            CryptoSpotTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EntryScreen()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        Timber.e("main activity onResume")
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun EntryScreen() {
        Timber.e("entry screen")
        val navController = rememberAnimatedNavController()
        val hasApiKey = remember {
            mutableStateOf(Prefs.apiKey.isNotBlank())
        }
        Timber.e("Dose user has key ? ${hasApiKey.value}")

        AnimatedNavHost(
            navController = navController,
            startDestination = if (hasApiKey.value) NavRoute.HOME_VIEW else NavRoute.LOGIN_VIEW
        ) {
            composable(
                route = NavRoute.HOME_VIEW,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                }
            ) {
                HomeScreen(navController)
            }

            composable(
                route = NavRoute.LOGIN_VIEW
            ) {
                LoginView(navController)

            }

            composable(
                route = NavRoute.FIND_CRYPTO_VIEW,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                }
            ) {
                CryptoListScreen(navController)
            }
        }


    }

    override fun onPause() {
        Timber.e("main onPause")
        super.onPause()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // TODO: onBackPressed may cause invoke onResume twice

    }
}




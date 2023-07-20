package com.kaiku.cryptospot.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
//import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.kaiku.cryptospot.BuildConfig
import com.kaiku.cryptospot.MultiTagTree
import com.kaiku.cryptospot.navigation.*
import com.kaiku.cryptospot.presentation.crypto_list.CryptoListScreen
import com.kaiku.cryptospot.presentation.home.HomeScreen
import com.kaiku.cryptospot.presentation.login.LoginScreen
import com.kaiku.cryptospot.presentation.test.*
import com.kaiku.cryptospot.presentation.pocket_hw.PocketHomeworkScreen
import com.kaiku.cryptospot.presentation.theme.CryptoSpotTheme
import com.kaiku.cryptospot.utils.ScreenAnimation.screenSlideEnter
import com.kaiku.cryptospot.utils.ScreenAnimation.screenSlideExit
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
            CryptoSpotTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavScreen()
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
    fun NavScreen() {
        Timber.e("Init nav screen")

        val hasApiKey = remember {
            mutableStateOf(Prefs.apiKey.isNotBlank())
        }
        Timber.e("Does user has key ? ${hasApiKey.value}")

        NavigationEffect(
            startDestination = if (hasApiKey.value) HomeDestination.route else LoginDestination.route
        ) {

            composable(
                route = HomeDestination.route,
                enterTransition = { screenSlideEnter(fromLeft = true) },
                exitTransition = { screenSlideExit(toLeft = true) }
            ) {
                HomeScreen()
            }
            composable(LoginDestination.route) {
                LoginScreen()
            }
            composable(
                route = FindCryptoDestination.route,
                enterTransition = { screenSlideEnter(fromLeft = false) },
                exitTransition = { screenSlideExit(toLeft = false) }
            ) {
                CryptoListScreen()
            }

            composable(
                route = TestDestination.route,
                arguments = TestDestination.arguments,
                enterTransition = { screenSlideEnter(fromLeft = false) },
                exitTransition = { screenSlideExit(toLeft = false) }
            ) {
                val channelID = it.arguments?.getString(TestScreenTag.CHANNEL_ID) ?: return@composable
                val score = it.arguments?.getInt(TestScreenTag.SCORE) ?: return@composable
                val isTesting = it.arguments?.getBoolean(TestScreenTag.IS_TESTING) ?: return@composable
                TestScreen(
                    channelID = channelID,
                    score = score,
                    isTesting = isTesting
                )
            }

            composable(
                route = PocketHomeworkDestination.route,
                enterTransition = { screenSlideEnter(fromLeft = false) },
                exitTransition = { screenSlideExit(toLeft = false) }
            ) {
                PocketHomeworkScreen()
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




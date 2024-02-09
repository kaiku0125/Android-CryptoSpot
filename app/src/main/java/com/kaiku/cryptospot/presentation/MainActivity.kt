package com.kaiku.cryptospot.presentation

//import androidx.navigation.compose.composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.composable
import com.kaiku.cryptospot.navigation.*
import com.kaiku.cryptospot.presentation.crypto_list.CryptoListScreenRoot
import com.kaiku.cryptospot.presentation.test.TestScreen
import com.kaiku.cryptospot.presentation.pocket_hw.PocketHomeworkScreen
import com.kaiku.cryptospot.presentation.flash.*
import com.kaiku.cryptospot.presentation.home.HomeScreenRoot
import com.kaiku.cryptospot.presentation.login.LoginScreenRoot
import com.kaiku.cryptospot.presentation.theme.CryptoSpotTheme
import com.kaiku.cryptospot.utils.ScreenAnimation.screenSlideEnter
import com.kaiku.cryptospot.utils.ScreenAnimation.screenSlideExit
import timber.log.Timber

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CryptoSpotTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavScreenGraph()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        Timber.e("main activity onResume")
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavScreenGraph() {
    Timber.e("Init nav screen graph")

    NavigationEffect(
        startDestination = HomeDestination.route
    ) {
        composable(
            route = HomeDestination.route,
            enterTransition = { screenSlideEnter(fromLeft = true) },
            exitTransition = { screenSlideExit(toLeft = true) }
        ) {
            HomeScreenRoot()
        }

        composable(
            route = TestDestination.route,
            enterTransition = { screenSlideEnter(fromLeft = false) },
            exitTransition = { screenSlideExit(toLeft = false) }
        ) {
            TestScreen()
        }
        composable(LoginDestination.route) {
            LoginScreenRoot()
        }
        composable(
            route = FindCryptoDestination.route,
            enterTransition = { screenSlideEnter(fromLeft = false) },
            exitTransition = { screenSlideExit(toLeft = false) }
        ) {
            CryptoListScreenRoot()
        }

        composable(
            route = FlashUIDestination.route,
            arguments = FlashUIDestination.arguments,
            enterTransition = { screenSlideEnter(fromLeft = false) },
            exitTransition = { screenSlideExit(toLeft = false) }
        ) {
            val channelID = it.arguments?.getString(FlashUIScreenTag.CHANNEL_ID) ?: return@composable
            val score = it.arguments?.getInt(FlashUIScreenTag.SCORE) ?: return@composable
            val isTesting = it.arguments?.getBoolean(FlashUIScreenTag.IS_TESTING) ?: return@composable
            FlashUIScreen(
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



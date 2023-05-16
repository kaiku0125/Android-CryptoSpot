package com.kaiku.cryptospot.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kaiku.cryptospot.BuildConfig
import com.kaiku.cryptospot.MultiTagTree
import com.kaiku.cryptospot.presentation.theme.CryptoSpotTheme
import com.kaiku.cryptospot.ui.theme.HomeView
import com.kaiku.cryptospot.ui.theme.LoginView
import com.kaiku.cryptospot.view.FindCryptoScreen
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
                    color = MaterialTheme.colors.background
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

    @Composable
    fun EntryScreen() {
        Timber.e("entry screen")
        val navController = rememberNavController()
        val hasApiKey = remember {
            mutableStateOf(Prefs.apiKey.isNotBlank())
        }
        Timber.e("Dose user has key ? ${hasApiKey.value}")
        NavHost(
            navController = navController,
            startDestination = if (hasApiKey.value) "HomeView" else "LoginView"
        ) {
            composable("HomeView") {
                HomeView(navController)
            }

            composable("LoginView") {
                LoginView(navController)

            }

            composable("FindCryptoView") {
                FindCryptoScreen(navController)
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




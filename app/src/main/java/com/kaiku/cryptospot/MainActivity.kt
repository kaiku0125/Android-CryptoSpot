package com.kaiku.cryptospot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kaiku.cryptospot.ui.theme.CryptoSpotTheme
import com.kaiku.cryptospot.ui.theme.HomeView
import com.kaiku.cryptospot.ui.theme.LoginView
import com.kaiku.cryptospot.view.FindCryptoView
import org.amobile.mqtt_k.prefs.Prefs
import timber.log.Timber

//adb connect 127.0.0.1:62001

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(BuildConfig.DEBUG)
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
    }

    @Composable
    fun EntryScreen(){
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "HomeView"){
            composable("HomeView"){
                HomeView(navController)
            }

            composable("LoginView"){
                LoginView(navController)
            }

            composable("FindCryptoView"){
                FindCryptoView(navController)
            }
        }

        if (Prefs.apiKey.isEmpty())
            navController.navigate("LoginView")
        else
            navController.navigate("HomeView")
    }
}




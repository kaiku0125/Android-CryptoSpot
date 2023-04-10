package com.kaiku.cryptospot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.kaiku.cryptospot.ui.theme.CryptoSpotTheme
import com.kaiku.cryptospot.ui.theme.EntryScreen
import org.amobile.mqtt_k.prefs.Prefs

//adb connect 127.0.0.1:62001

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}




package com.kaiku.cryptospot.presentation.test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kaiku.cryptospot.navigation.HomeDestination
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.presentation.theme.text_15sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(
    channelID : String,
    score :Int,
    isTesting : Boolean
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "test")
                },
                navigationIcon ={
                    IconButton(
                        onClick = {
                            ScreenNavigator.back(HomeDestination.route)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(
                text = "$channelID,__ $score,__ $isTesting",
                style = MaterialTheme.typography.text_15sp
            )

        }

    }
}

object TestScreenTag{
    const val CHANNEL_ID = "channel_id"
    const val SCORE = "score"
    const val IS_TESTING = "is_testing"
}


package com.kaiku.cryptospot.presentation.pocket_hw

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FirstScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )

        ValidTimeSpinnerView()

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )

        ValidTimeWithDateSpinnerView()
    }
}

@Composable
fun SecondScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )
        ValidTimeWithDateSpinnerView()



        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )

        Text("Second line")

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )

        ValidTimeSpinnerView()
    }
}

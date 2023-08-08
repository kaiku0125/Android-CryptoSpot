package com.kaiku.cryptospot.presentation.pocket_hw

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FirstTabSpinnerView(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )

        ChoseCryptoSpinnerView()

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )

        ChoseCryptoSpinnerWithTextFieldView()
    }
}

@Composable
fun SecondTabSpinnerView(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )
        ChoseCryptoSpinnerWithTextFieldView()



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

        ChoseCryptoSpinnerWithTextFieldView()
    }
}

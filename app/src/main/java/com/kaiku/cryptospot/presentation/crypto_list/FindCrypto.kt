package com.kaiku.cryptospot.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kaiku.cryptospot.presentation.crypto_list.CryptoListViewModel
import timber.log.Timber

private const val TAG = "FindCrypto"

@Composable
fun FindCryptoView(nav: NavController) {
    val ctx = LocalContext.current
    val viewModel : CryptoListViewModel = hiltViewModel()
    val mCryptoList = viewModel.apiCryptoList.observeAsState(emptyList())


    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            Button(onClick = {viewModel.refreshCryptoList()}) {
                Text(text = "refresh")
            }


            if (mCryptoList.value.isEmpty()) {
                Timber.e("MessageUI: database is empty")
            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    itemsIndexed(mCryptoList.value) { index, info ->
                        InfoItem(index = index, info = info)
                    }
                }
            }
        }
    }

}

@Composable
fun InfoItem(index: Int, info: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "編號 : ${(index + 1).toString()}", fontWeight = FontWeight.Bold)
            Text(text = "幣 -> ${info.toString()}")
        }
    }
}

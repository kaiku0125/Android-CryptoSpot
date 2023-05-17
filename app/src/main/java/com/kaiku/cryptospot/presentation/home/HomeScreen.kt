package com.kaiku.cryptospot.presentation.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import timber.log.Timber

@Composable
fun HomeScreen(nav: NavController) {
    Timber.e("Now in home view")
    Scaffold(topBar = { MyTopBar(nav) }) { padding ->

        Button(modifier = Modifier
            .padding(padding)
            .padding(start = 20.dp, top = 50.dp),
            onClick = {
//                nav.navigate("LoginView")
            }) {
            Text(text = "test")
        }

    }
}

@Composable
fun MyTopBar(nav: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "現貨損益",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        },
        modifier = Modifier.fillMaxWidth(),
        actions = {
            IconButton(onClick = { nav.navigate("FindCryptoView") }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    )
}


//suspend fun connect() {
//    val interceptor = Interceptor { chain ->
//        val request = chain.request()
//        val newUrl = request.url().newBuilder()
//            .addQueryParameter("CMC_PRO_API_KEY", MY_API_KEY)
//            .build()
//        val newRequest = request.newBuilder()
//            .url(newUrl)
//            .build()
//        chain.proceed(newRequest)
//    }
//    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
//
//    val retrofit = Retrofit.Builder()
//        .baseUrl(Global.COIN_MARKET_CAP_BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .client(client)
//        .build()
//
//    val coinMarketCapApi = retrofit.create(CoinMarketCapApi::class.java)
//
//    try {
//        Log.e(TAG, "connect: start")
//        val call = coinMarketCapApi.getCurrentPrice("BTC,ETH,BNB")
////        val call = coinMarketCapApi.getListCryptoPrices(listOf("BTC","ETH","BNB"))
//
//
//        call.enqueue(object : Callback<CoinMarketCapResponse> {
//            override fun onResponse(
//                call: Call<CoinMarketCapResponse>,
//                response: Response<CoinMarketCapResponse>
//            ) {
//                val coinMarketCapResponse = response.body()
//                Log.e(TAG, "onResponse: body ➔ ${coinMarketCapResponse.toString()}")
//
//                val bitcoinPrice = coinMarketCapResponse?.data?.get("BTC")?.quote?.get("USD")?.price
//                val ethcoinPrice = coinMarketCapResponse?.data?.get("ETH")?.quote?.get("USD")?.price
//                val bnbcoinPrice = coinMarketCapResponse?.data?.get("BNB")?.quote?.get("USD")?.price
//
//                Log.e(TAG, "onResponse: BTC ➔ $bitcoinPrice")
//                Log.e(TAG, "onResponse: ETH ➔ $ethcoinPrice")
//                Log.e(TAG, "onResponse: BNB ➔ $bnbcoinPrice")
////                Log.e(TAG, "onResponse: 666666666666")
//                // Use the Bitcoin price data
//            }
//
//            override fun onFailure(call: Call<CoinMarketCapResponse>, t: Throwable) {
//                Log.e(TAG, "onFailure: GGGGGGGGG")
//                // Handle the failure
//            }
//        })
//
//
//    } catch (e: Exception) {
//        Log.e(TAG, "error: ${e.message}")
//    }
//
//}

//suspend fun getList() {
//    val interceptor = Interceptor { chain ->
//        val request = chain.request()
//        val newUrl = request.url().newBuilder()
//            .addQueryParameter("CMC_PRO_API_KEY", MY_API_KEY)
//            .build()
//        val newRequest = request.newBuilder()
//            .url(newUrl)
//            .build()
//        chain.proceed(newRequest)
//    }
//    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
//
//    val retrofit = Retrofit.Builder()
//        .baseUrl(Global.COIN_MARKET_CAP_BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .client(client)
//        .build()
//
//    val coinMarketCapApi = retrofit.create(CoinMarketCapApi::class.java)
//
//    try {
//        Log.e(TAG, "connect: start")
//        val call = coinMarketCapApi.getCryptoListings()
//
//        call.enqueue(object : Callback<CoinMarketCapResponse2> {
//            override fun onResponse(
//                call: Call<CoinMarketCapResponse2>,
//                response: Response<CoinMarketCapResponse2>
//            ) {
//                val coinMarketCapResponse = response.body()
//                Log.e(TAG, "onResponse: body ➔ ${coinMarketCapResponse.toString()}")
//
//                if (coinMarketCapResponse?.status?.error_code != null) {
//                    Log.e(TAG, "getList: something went wrong, error code ➔ ${coinMarketCapResponse.status.error_code} , msg -> ${coinMarketCapResponse.status.error_message}")
//                }
//                val cryptoListings = coinMarketCapResponse?.data
//                val cryptoSymbols = cryptoListings?.map { it.symbol }
//
//
//                Log.e(TAG, "getList: symbols ➔ $cryptoSymbols , size ➔ ${cryptoSymbols?.size}")
//
//
//            }
//
//            override fun onFailure(call: Call<CoinMarketCapResponse2>, t: Throwable) {
//                Log.e(TAG, "onFailure: GGGGGGGGG")
//                // Handle the failure
//            }
//        })
//
//
//    } catch (e: Exception) {
//        Log.e(TAG, "error: ${e.message}")
//    }
//
//}
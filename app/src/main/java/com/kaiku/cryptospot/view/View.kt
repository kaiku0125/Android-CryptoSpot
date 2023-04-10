package com.kaiku.cryptospot.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kaiku.cryptospot.CoinMarketCapApi
import com.kaiku.cryptospot.data.CoinMarketCapResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.amobile.mqtt_k.prefs.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainView"
private const val MY_API_KEY = "2f33263a-ee2a-40ff-8795-066fd9e38167"
private const val COIN_MARKET_CAP_BASE_URL = "https://pro-api.coinmarketcap.com/"

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
    }

    if (Prefs.apiKey.isEmpty())
        navController.navigate("LoginView")
    else
        navController.navigate("HomeView")
}
@Composable
fun HomeView(nav : NavController) {
    Scaffold(topBar = { MyTopBar() }) { padding ->

        Button(modifier = Modifier
            .padding(padding)
            .padding(start = 20.dp, top = 50.dp),
            onClick = {
//                GlobalScope.launch(Dispatchers.IO) { connect() }
                nav.navigate("LoginView")
            }) {
            Text(text = "test")
        }

    }
}

@Composable
fun MyTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "現貨損益",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

fun connect(){
    val interceptor = Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url().newBuilder()
            .addQueryParameter("CMC_PRO_API_KEY", MY_API_KEY)
            .build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val retrofit = Retrofit.Builder()
        .baseUrl(COIN_MARKET_CAP_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val coinMarketCapApi = retrofit.create(CoinMarketCapApi::class.java)

    try {
        Log.e(TAG, "connect: start")
        val call = coinMarketCapApi.getCurrentPrice("BTC,ETH,BNB")
//        val call = coinMarketCapApi.getListCryptoPrices(listOf("BTC","ETH","BNB"))


        call.enqueue(object : Callback<CoinMarketCapResponse> {
            override fun onResponse(call: Call<CoinMarketCapResponse>, response: Response<CoinMarketCapResponse>) {
                val coinMarketCapResponse = response.body()
                Log.e(TAG, "onResponse: body -> ${coinMarketCapResponse.toString()}")

                val bitcoinPrice = coinMarketCapResponse?.data?.get("BTC")?.quote?.get("USD")?.price
                val ethcoinPrice = coinMarketCapResponse?.data?.get("ETH")?.quote?.get("USD")?.price
                val bnbcoinPrice = coinMarketCapResponse?.data?.get("BNB")?.quote?.get("USD")?.price

                Log.e(TAG, "onResponse: BTC -> $bitcoinPrice")
                Log.e(TAG, "onResponse: ETH -> $ethcoinPrice")
                Log.e(TAG, "onResponse: BNB -> $bnbcoinPrice")
//                Log.e(TAG, "onResponse: 666666666666")
                // Use the Bitcoin price data
            }
            override fun onFailure(call: Call<CoinMarketCapResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: GGGGGGGGG")
                // Handle the failure
            }
        })



    }catch (e : Exception){
        Log.e(TAG, "error: ${e.message}")
    }



}
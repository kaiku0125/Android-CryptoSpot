package com.kaiku.cryptospot.model

import android.content.Context
import android.provider.ContactsContract.AggregationExceptions
import android.util.Log
import com.kaiku.cryptospot.CoinMarketCapApi
import com.kaiku.cryptospot.Global
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.amobile.mqtt_k.prefs.Prefs
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.sql.Time
import kotlin.math.log

private const val TAG = "MainRepository"

class MainRepository(private val ctx: Context) {
    private val api: CoinMarketCapApi = ApiAgent.getInstance()
    var cryptoList: List<String> = emptyList()

//    suspend fun requestCryptoList(): List<String> {
//
//        CoroutineScope(Dispatchers.IO).launch {
//
//            val response = api.getCryptoListings().awaitResponse()
//
//            if (response.isSuccessful) {
//                val body = response.body()!!
//                Log.e(TAG, "onResponse: body ➔ ${body.toString()}")
//
//                val cryptoListings = body.data
//                val cryptoSymbols = cryptoListings.map { it.symbol }
//
//                cryptoList = cryptoSymbols
//                Log.e(TAG, "getList: symbols ➔ $cryptoSymbols , size ➔ ${cryptoSymbols.size}")
//            }
//
//        }
//        Log.e(TAG, "requestCryptoList: end scope")
//        return cryptoList
//    }

//    suspend fun requestCryptoList(): List<String> {
//        Timber.e("Method start")
//        val response = api.getCryptoListings()
//
//        if (response.isSuccessful) {
//            val body = response.body()!!
//            Timber.e("onResponse: body ➔ ${body.toString()}")
//
//            val cryptoListings = body.data
//            val cryptoSymbols = cryptoListings.map { it.symbol }
//
//            cryptoList = cryptoSymbols
//            Timber.e("getList: symbols ➔ $cryptoSymbols , size ➔ ${cryptoSymbols.size}")
//        }
//
//        Timber.e("Method end")
//        return cryptoList
//    }

    suspend fun requestCryptoList(): List<String> {
        Timber.e("Method start")
        val response = api.getCryptoListings()

        Timber.e("onResponse: body ➔ ${response.toString()}")

        val cryptoListings = response.data
        val cryptoSymbols = cryptoListings.map { it.symbol }

        cryptoList = cryptoSymbols
        Timber.e("getList: symbols ➔ $cryptoSymbols , size ➔ ${cryptoSymbols.size}")


        Timber.e("Method end")
        return cryptoList
    }

    suspend fun requestObserverList(symbol: String) {

    }

}
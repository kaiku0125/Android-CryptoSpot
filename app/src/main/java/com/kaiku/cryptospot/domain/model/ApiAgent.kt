package com.kaiku.cryptospot.domain.model

import com.kaiku.cryptospot.data.remote.CoinMarketCapApi
import com.kaiku.cryptospot.common.Global
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.amobile.mqtt_k.prefs.Prefs
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiAgent private constructor() {


    companion object {
        private var instance: CoinMarketCapApi? = null

        fun getInstance() : CoinMarketCapApi {
            if (instance == null) {
                val interceptor = Interceptor { chain ->
                    val request = chain.request()
                    val newUrl = request.url().newBuilder()
                        .addQueryParameter("CMC_PRO_API_KEY", Prefs.apiKey)
                        .build()
                    val newRequest = request.newBuilder()
                        .url(newUrl)
                        .build()
                    chain.proceed(newRequest)
                }
                val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(Global.COIN_MARKET_CAP_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                instance = retrofit.create(CoinMarketCapApi::class.java)
            }
            return instance as CoinMarketCapApi
        }


    }
}
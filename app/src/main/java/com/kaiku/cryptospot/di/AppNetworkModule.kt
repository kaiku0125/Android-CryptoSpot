package com.kaiku.cryptospot.di

import com.kaiku.cryptospot.common.Global
import com.kaiku.cryptospot.data.prefs.Prefs
import com.kaiku.cryptospot.data.remote.CoinMarketCapApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

val appNetworkModule = module {
    single<CoinMarketCapApi> {
        createCoinMarketCapApi(
           prefs = get()
        )
    }
}

private fun createCoinMarketCapApi(prefs: Prefs): CoinMarketCapApi {

    val interceptor = Interceptor { chain ->
        val request = chain.request()
        Timber.e("Provide : apiKey : ${prefs.apiKey}")
        val newUrl = request.url().newBuilder()
            .addQueryParameter("CMC_PRO_API_KEY", prefs.apiKey)
            .build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    return Retrofit.Builder().apply {
        baseUrl(Global.COIN_MARKET_CAP_BASE_URL)
        addConverterFactory(GsonConverterFactory.create())
        client(client)
    }.build().create(CoinMarketCapApi::class.java)
}


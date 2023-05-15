package com.kaiku.cryptospot.di

import com.kaiku.cryptospot.common.Global
import com.kaiku.cryptospot.data.remote.CoinMarketCapApi
import com.kaiku.cryptospot.data.repository.MainRepositoryImpl
import com.kaiku.cryptospot.data.repository.MainRepositoryImpl_Factory
import com.kaiku.cryptospot.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.amobile.mqtt_k.prefs.Prefs
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoinMarketCapApi(): CoinMarketCapApi {
        val interceptor = Interceptor { chain ->
            val request = chain.request()
            Timber.e("Provide : apiKey : ${Prefs.apiKey}")
            val newUrl = request.url().newBuilder()
                .addQueryParameter("CMC_PRO_API_KEY", Prefs.apiKey)
                .build()
            val newRequest = request.newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(Global.COIN_MARKET_CAP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CoinMarketCapApi::class.java)

    }

    @Provides
    @Singleton
    fun provideMainRepository(api: CoinMarketCapApi): MainRepository {
        return MainRepositoryImpl(api)
    }
}
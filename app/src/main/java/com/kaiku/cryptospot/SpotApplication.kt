package com.kaiku.cryptospot

import android.app.Application
import com.kaiku.cryptospot.di.appNetworkModule
import com.kaiku.cryptospot.di.appRepositoryModule
import com.kaiku.cryptospot.di.appUseCaseModule
import com.kaiku.cryptospot.di.appViewModelModule
import org.amobile.mqtt_k.prefs.Prefs
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import timber.log.Timber

class SpotApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(MultiTagTree())
        }

        Prefs.load(this)

        startKoin {
            androidContext(this@SpotApplication)
            loadKoinModules(
                listOf(
                    appNetworkModule,
                    appViewModelModule,
                    appUseCaseModule,
                    appRepositoryModule
                )
            )
        }
    }
}
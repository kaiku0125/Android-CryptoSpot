package com.kaiku.cryptospot

import android.app.Application
import com.kaiku.cryptospot.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import timber.log.Timber

class SpotApplication : Application() {
    override fun onCreate() {
        super.onCreate()

//        if (BuildConfig.DEBUG) {
//            Timber.plant(MultiTagTree())
//        }
        Timber.plant(MultiTagTree())

        startKoin {
            androidContext(this@SpotApplication)
            loadKoinModules(
                listOf(
                    appSingleModule,      // Prefs
                    appDatabaseModule,    // Database
                    appNetworkModule,     // API
                    appRepositoryModule,  // Repositories
                    appUseCaseModule,     // Use Cases
                    appViewModelModule    // ViewModels
                )
            )
        }
    }
}
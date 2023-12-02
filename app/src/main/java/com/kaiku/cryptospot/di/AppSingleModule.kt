package com.kaiku.cryptospot.di

import com.kaiku.cryptospot.data.prefs.Prefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appSingleModule = module {
    single<Prefs>{
        Prefs(
            context = androidContext()
        )
    }
}
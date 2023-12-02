package com.kaiku.cryptospot.di

import com.kaiku.cryptospot.data.repository.MainRepositoryImpl
import com.kaiku.cryptospot.domain.repository.MainRepository
import org.koin.dsl.module

val appRepositoryModule = module {
    factory<MainRepository> {
        MainRepositoryImpl(
            api = get()
        )
    }
}
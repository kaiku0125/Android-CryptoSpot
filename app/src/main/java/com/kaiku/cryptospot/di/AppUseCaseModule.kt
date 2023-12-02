package com.kaiku.cryptospot.di

import com.kaiku.cryptospot.domain.use_case.GetCryptoListUseCase
import org.koin.dsl.module

val appUseCaseModule = module {
    factory<GetCryptoListUseCase> {
        GetCryptoListUseCase(
            mainRepository = get()
        )
    }
}
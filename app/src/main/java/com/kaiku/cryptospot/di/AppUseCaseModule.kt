package com.kaiku.cryptospot.di

import com.kaiku.cryptospot.domain.usecase.AddHoldingUseCase
import com.kaiku.cryptospot.domain.usecase.GetCryptoListUseCase
import org.koin.dsl.module

val appUseCaseModule = module {
    factory<GetCryptoListUseCase> {
        GetCryptoListUseCase(
            mainRepository = get()
        )
    }

    factory<AddHoldingUseCase> {
        AddHoldingUseCase(
            userHoldingRepository = get()
        )
    }
}
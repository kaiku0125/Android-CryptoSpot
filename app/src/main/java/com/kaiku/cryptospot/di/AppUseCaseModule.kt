package com.kaiku.cryptospot.di

import com.kaiku.cryptospot.domain.usecase.AddHoldingUseCase
import com.kaiku.cryptospot.domain.usecase.FetchLatestCryptoListingUseCase
import com.kaiku.cryptospot.domain.usecase.GetCryptoListingPagerUseCase
import org.koin.dsl.module

val appUseCaseModule = module {
    factory {
        FetchLatestCryptoListingUseCase(
            mainRepository = get()
        )
    }

    factory<GetCryptoListingPagerUseCase> {
        GetCryptoListingPagerUseCase(
            mainRepository = get()
        )
    }

    factory<AddHoldingUseCase> {
        AddHoldingUseCase(
            userHoldingRepository = get()
        )
    }
}
package com.kaiku.cryptospot.di

import com.kaiku.cryptospot.presentation.MainActivityViewModel
import com.kaiku.cryptospot.presentation.crypto_list.CryptoListViewModel
import com.kaiku.cryptospot.presentation.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appViewModelModule = module {
    viewModel{
        MainActivityViewModel(
            fetchLatestCryptoListingUseCase = get()
        )
    }

    viewModel{
        LoginViewModel(
            prefsRepository = get()
        )
    }

    viewModel {
        CryptoListViewModel(
            pager = get(),
            addHoldingUseCase = get()
        )
    }
}
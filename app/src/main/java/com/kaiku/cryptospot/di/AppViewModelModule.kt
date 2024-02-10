package com.kaiku.cryptospot.di

import com.kaiku.cryptospot.presentation.crypto_list.CryptoListViewModel
import com.kaiku.cryptospot.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appViewModelModule = module {
    viewModel{
        LoginViewModel(
            prefsRepository = get()
        )
    }

    viewModel {
        CryptoListViewModel(
            getCryptoListUseCase = get(),
            pager = get()
        )
    }


}
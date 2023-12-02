package com.kaiku.cryptospot.di

import com.kaiku.cryptospot.presentation.crypto_list.CryptoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appViewModelModule = module {
    viewModel {
        CryptoListViewModel(
            getCryptoListUseCase = get()
        )
    }


}
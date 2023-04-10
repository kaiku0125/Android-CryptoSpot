package com.kaiku.cryptospot

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CryptoViewModelFactory(private val ctx : Context) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CryptoViewModel(ctx) as T
    }
}
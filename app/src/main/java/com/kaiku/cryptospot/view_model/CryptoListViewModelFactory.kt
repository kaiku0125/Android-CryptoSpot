package com.kaiku.cryptospot.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber

private const val TAG = "CListVMFactory"
class CryptoListViewModelFactory(private val ctx :Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Timber.e("create: cryptoListViewModel...")
        return CryptoListViewModel(ctx) as T
    }
}
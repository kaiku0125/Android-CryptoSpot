package com.kaiku.cryptospot.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

private const val TAG = "HomeViewModelFactory"
class HomeViewModelFactory(private val ctx : Context) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.e(TAG, "create: homeViewModel...")
        return HomeViewModel(ctx) as T
    }
}
package com.kaiku.cryptospot.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaiku.cryptospot.domain.repository.MainRepository
import com.kaiku.cryptospot.domain.usecase.FetchLatestCryptoListingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivityViewModel(
    private val fetchLatestCryptoListingUseCase: FetchLatestCryptoListingUseCase
) : ViewModel() {


    fun onResume() {
        Timber.tag("wtf").d("onResume")
        startPeriodicFetch()
    }

    fun onPause() {
        Timber.tag("wtf").d("onPause")
        fetchJob?.cancel()
    }

    private var fetchJob: Job? = null
    private fun startPeriodicFetch() {
        Timber.tag("wtf").d("startPeriodicFetch")
        fetchJob = viewModelScope.launch {
            while (isActive) {
                fetchLatestCryptoListingUseCase.invoke()
                delay(60 * 60 * 1000L) // 1 小時
            }
        }
    }

}
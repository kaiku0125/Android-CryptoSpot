package com.kaiku.cryptospot.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaiku.cryptospot.model.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val TAG = "CryptoListViewModel"

class CryptoListViewModel(private val ctx: Context) : ViewModel() {
    private val mainRepository: MainRepository = MainRepository(ctx)


    private val _apiCryptoList: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    val apiCryptoList : LiveData<List<String>> = _apiCryptoList


    init {
        Timber.e("list -> ${apiCryptoList.value?.size}")

    }

    fun refreshCryptoList() {
        Timber.e("refresh start!")
        viewModelScope.launch(Dispatchers.IO) {
            Timber.e("Scope start -----")
            val data = mainRepository.requestCryptoList()
            Timber.e(" ~ middle ~ ")
            _apiCryptoList.postValue(data)

            Timber.e("Scope end -----")
        }
        Timber.e("refresh end!")
    }

    suspend fun saveCryptoListToDb() {

    }
}
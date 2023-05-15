package com.kaiku.cryptospot.presentation.crypto_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaiku.cryptospot.data.repository.MainRepositoryImpl
import com.kaiku.cryptospot.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

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
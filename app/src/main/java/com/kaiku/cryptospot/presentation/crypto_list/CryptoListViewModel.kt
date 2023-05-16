package com.kaiku.cryptospot.presentation.crypto_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaiku.cryptospot.common.Resource
import com.kaiku.cryptospot.domain.use_case.GetCryptoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val getCryptoListUseCase: GetCryptoListUseCase
) : ViewModel() {

    private val _apiCryptoListState = mutableStateOf(CryptoListState())
    val apiCryptoListState: State<CryptoListState> = _apiCryptoListState


    init {
        Timber.e("list -> ${apiCryptoListState.value.cryptoList.size}")
    }

    fun refreshCryptoList() {
        Timber.e("refresh crypto list")
        getCryptoListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Timber.e("Success ! ${result.data?.size}")
                    _apiCryptoListState.value =
                        CryptoListState(cryptoList = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _apiCryptoListState.value =
                        CryptoListState(error = result.message ?: "An unexpected error occurred.")
                }
                is Resource.Loading -> {
                    Timber.e("Loading")
                    _apiCryptoListState.value = CryptoListState(isLoading = true)

                }
            }
        }.launchIn(viewModelScope)

    }

    suspend fun saveCryptoListToDb() {

    }
}
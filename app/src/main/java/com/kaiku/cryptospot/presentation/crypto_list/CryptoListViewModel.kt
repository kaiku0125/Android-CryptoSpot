package com.kaiku.cryptospot.presentation.crypto_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaiku.cryptospot.common.Resource
import com.kaiku.cryptospot.domain.use_case.GetCryptoListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import timber.log.Timber

class CryptoListViewModel(
    private val getCryptoListUseCase: GetCryptoListUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(CryptoListState())
    val viewState = _viewState.asStateFlow()

    init {
        initCryptoList()
    }

    private fun initCryptoList() {
        Timber.e("refresh crypto list")
        getCryptoListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Timber.e("Success ! ${result.data?.size}")
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            cryptoList = result.data ?: emptyList()
                        )
                    }
                }
                is Resource.Error -> {
                    Timber.tag(TAG).e("Error ! ${result.message ?: "An unexpected error occurred."} ")
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            cryptoList = emptyList(),
                            errorMsg = "取價異常，請稍夠再試"
                        )
                    }
                }
                is Resource.Loading -> {
                    _viewState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)

    }

    suspend fun saveCryptoListToDb() {

    }

    companion object {
        private const val TAG = "CryptoListViewModel"
    }
}
package com.kaiku.cryptospot.presentation.crypto_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.kaiku.cryptospot.common.Resource
import com.kaiku.cryptospot.data.db.CryptoListingDatabase
import com.kaiku.cryptospot.data.db.CryptoListingEntity
import com.kaiku.cryptospot.data.remote.dto.crypto_list.toData
import com.kaiku.cryptospot.domain.use_case.GetCryptoListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class CryptoListViewModel(
    private val getCryptoListUseCase: GetCryptoListUseCase,
    private val pager: Pager<Int, CryptoListingEntity>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _viewState = MutableStateFlow(CryptoListState())
    val viewState = _viewState.asStateFlow()

    val pagerFlow = pager.flow
        .map { pagingData ->
            pagingData.map { it.toData() }
        }
        .flowOn(dispatcher)
        .cachedIn(viewModelScope)

    init {
//        initCryptoList()
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
                    Timber.tag(TAG)
                        .e("Error ! ${result.message ?: "An unexpected error occurred."} ")
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

    companion object {
        private const val TAG = "CryptoListViewModel"
    }
}
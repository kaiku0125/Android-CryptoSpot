package com.kaiku.cryptospot.presentation.crypto_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.kaiku.cryptospot.common.Resource
import com.kaiku.cryptospot.data.db.cryptolisting.CryptoListingEntity
import com.kaiku.cryptospot.data.remote.dto.crypto_list.toData
import com.kaiku.cryptospot.domain.usecase.AddHoldingUseCase
import com.kaiku.cryptospot.domain.usecase.GetCryptoListUseCase
import com.kaiku.cryptospot.presentation.crypto_list.data.CryptoListViewAction
import com.kaiku.cryptospot.presentation.crypto_list.data.CryptoListViewEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    private val addHoldingUseCase: AddHoldingUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _viewState = MutableStateFlow(CryptoListState())
    val viewState = _viewState.asStateFlow()

    private val _viewEvent = MutableSharedFlow<CryptoListViewEvent?>()
    val viewEvent = _viewEvent.asSharedFlow()

    val pagerFlow = pager.flow
        .map { pagingData ->
            pagingData.map { it.toData() }
        }
        .onEach {

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

    fun dispatch(action: CryptoListViewAction) {
        viewModelScope.launch(dispatcher) {
            when(action) {
                is CryptoListViewAction.OnCardClickAction -> {
                    _viewEvent.emit(
                        CryptoListViewEvent.ShowAddUserHoldingsDialog(action.data)
                    )
                }

                is CryptoListViewAction.ResetDialogAction -> {
                    _viewEvent.emit(CryptoListViewEvent.HideDialog)
                }

                is CryptoListViewAction.AddHoldingAction -> {
                    addHoldingUseCase.invoke(holding = action.holding).fold(
                        onSuccess = { holding ->
                            holding?.let {
                                Timber.tag("wtf").d("[${it.symbol}]: ${it.amount}, ${it.cost}")
                            }
                            _viewEvent.emit(CryptoListViewEvent.HideDialog)
                        },
                        onFailure = {
                            Timber.tag("wtf").e("addHoldingUseCase error: ${it.message}")
                        }
                    )
                }

                else -> Unit
            }
        }
    }


    companion object {
        private const val TAG = "CryptoListViewModel"
    }
}
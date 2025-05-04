package com.kaiku.cryptospot.presentation.crypto_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kaiku.cryptospot.domain.model.Holding
import com.kaiku.cryptospot.domain.usecase.AddHoldingUseCase
import com.kaiku.cryptospot.domain.usecase.GetCryptoListingPagerUseCase
import com.kaiku.cryptospot.presentation.crypto_list.data.CryptoListViewAction
import com.kaiku.cryptospot.presentation.crypto_list.data.CryptoListViewEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class CryptoListViewModel(
    private val getCryptoListingPagerUseCase: GetCryptoListingPagerUseCase,
    private val addHoldingUseCase: AddHoldingUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _viewState = MutableStateFlow(CryptoListViewState.INIT)
    val viewState = _viewState.asStateFlow()

    private val _viewEvent = MutableSharedFlow<CryptoListViewEvent?>()
    val viewEvent = _viewEvent.asSharedFlow()

    private val _searchQuery = MutableStateFlow("")

    val pagerFlow = _searchQuery
        .onEach { onInputSearchText(text = it) }
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getCryptoListingPagerUseCase.invoke(query = query)
        }
        .flowOn(dispatcher)
        .cachedIn(viewModelScope)

    fun dispatch(action: CryptoListViewAction) {
        viewModelScope.launch(dispatcher) {
            when (action) {
                is CryptoListViewAction.OnCardClickAction -> {
                    _viewEvent.emit(
                        CryptoListViewEvent.ShowAddUserHoldingsDialog(action.data)
                    )
                }

                is CryptoListViewAction.ResetDialogAction -> {
                    _viewEvent.emit(CryptoListViewEvent.HideDialog)
                }

                is CryptoListViewAction.AddHoldingAction -> onAddHolding(newHolding = action.holding)

                is CryptoListViewAction.InputSearchTextAction -> _searchQuery.emit(action.text)

                else -> Unit
            }
        }
    }

    private fun onAddHolding(newHolding: Holding) {
        viewModelScope.launch {
            addHoldingUseCase.invoke(holding = newHolding).fold(
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
    }

    private fun onInputSearchText(text: String) {
        viewModelScope.launch {
            _viewState.update {
                it.copy(
                    searchText = text
                )
            }
        }
    }


    companion object {
        private const val TAG = "CryptoListViewModel"
    }
}
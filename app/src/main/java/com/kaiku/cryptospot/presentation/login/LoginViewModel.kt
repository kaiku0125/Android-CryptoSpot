package com.kaiku.cryptospot.presentation.login

import androidx.lifecycle.ViewModel
import com.kaiku.cryptospot.data.prefs.IPrefsRepository
import com.kaiku.cryptospot.presentation.login.data.LoginViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(
    val prefsRepository: IPrefsRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState = _viewState.asStateFlow()

    init {
        _viewState.update {
            it.copy(
                apiKey = prefsRepository.getApiKey()
            )
        }
    }

    fun updateApiKey(key: String){
        prefsRepository.setApiKey(key = key)
    }
}
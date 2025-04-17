package com.kaiku.composecomponent.view.login.data

import androidx.compose.ui.graphics.Color

data class LoginViewState(
    val logoColor: Color,
    val identity : String = "",
    val identityDisplay : String = "",
    val password : String = "",
    val isRememberIdChecked : Boolean = false,
    val isBiometricEnable: Boolean = false,
    val isLoginValid: Boolean = false,
    val buildVersion: String = ""
)

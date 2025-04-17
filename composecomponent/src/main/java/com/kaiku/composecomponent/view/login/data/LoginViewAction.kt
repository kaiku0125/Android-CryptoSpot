package com.kaiku.composecomponent.view.login.data

sealed class LoginViewAction {

    data object ForgetPasswordAction : LoginViewAction()

    data object BiometricLoginClick : LoginViewAction()

    data object OpenAccountAction : LoginViewAction()

    data object LoginAction : LoginViewAction()

    data object CMoneyLoginAction : LoginViewAction()

    data object FacebookLoginAction : LoginViewAction()

    data object GoogleLoginAction : LoginViewAction()

    data object PrivacyStatementAction : LoginViewAction()

    data object PersonalProtection : LoginViewAction()

    data object AccountOpeningQueryAction : LoginViewAction()

    data object ContactCustomerServiceAction : LoginViewAction()

    data object AnnouncementAction : LoginViewAction()

    data object AntiFraudAction : LoginViewAction()

    // ---------------------- 原生viewRequest ---------------------- //
    data object BuildConfigClickAction : LoginViewAction()

    data class UpdateIdentityFocusAction(
        val isFocus: Boolean
    ) : LoginViewAction()

    data class UserInputIdentityAction(
        val identity : String
    ): LoginViewAction()

    data class UserInputPasswordAction(
        val password : String
    ): LoginViewAction()

    data class UpdateRememberIdAction(
        val isChecked : Boolean
    ) : LoginViewAction()
}
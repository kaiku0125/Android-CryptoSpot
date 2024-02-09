package com.kaiku.cryptospot.presentation.home.data

sealed class HomeViewAction {

    data class NavigateAction(
        val destination: String
    ) : HomeViewAction()

    object FinishAction : HomeViewAction()
}
package com.kaiku.composecomponent.view.personalaccount.data

sealed class PersonalAccountViewAction {

    data object OnRefreshAction : PersonalAccountViewAction()
    data object OnToggleVisibilityAction : PersonalAccountViewAction()
    data object OnPromoteClickAction: PersonalAccountViewAction()
    data object OnMonthTradeAmountClickAction: PersonalAccountViewAction()
    data object OnBalanceClickAction: PersonalAccountViewAction()

}
package com.kaiku.composecomponent.view.microcapital.data

sealed class MicroCapitalViewAction {
    data object ReRegisterAction : MicroCapitalViewAction()

    data object CancelAction: MicroCapitalViewAction()

    data object ShowMoreAction: MicroCapitalViewAction()
}
package com.kaiku.composecomponent.view.mgm.data

sealed class MgmViewAction {

    data object CloseAction : MgmViewAction()

    data object CopyCodeAction : MgmViewAction()

    data object ShareLinkAction : MgmViewAction()

    data object GotoMissionAction : MgmViewAction()

    data object GotoPocketEventAction : MgmViewAction()
}
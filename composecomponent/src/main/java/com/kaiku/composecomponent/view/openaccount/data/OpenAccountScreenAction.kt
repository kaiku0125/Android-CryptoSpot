package com.kaiku.composecomponent.view.openaccount.data


sealed class OpenAccountScreenAction {
    data object CloseClick : OpenAccountScreenAction()

    data class OnEventClick(
        val area: OpenAccountClickArea
    ) : OpenAccountScreenAction()
}
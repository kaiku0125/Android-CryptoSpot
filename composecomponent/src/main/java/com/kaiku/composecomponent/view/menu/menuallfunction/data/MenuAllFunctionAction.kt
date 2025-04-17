package com.kaiku.composecomponent.view.menu.menuallfunction.data

import com.kaiku.composecomponent.component.grid.data.HomeMenuGridBaseItem

sealed class ScreenAction {
    data object Back : ScreenAction()

}

sealed class ViewModelAction {
    data class ManageMode(
        val isSave: Boolean
    ) : ViewModelAction()

    data class ClickHomeMenuItem(
        val key: String
    ) : ViewModelAction()

    data class ModifyItem(
        val key: String
    ) : ViewModelAction()

    data class UpdateHomeMenuItem(
        val homeMenuItemList: List<HomeMenuGridBaseItem>
    ) : ViewModelAction()

    data class RemoveHomeMenuItem(
        val key: String
    ) : ViewModelAction()

    data object None : ViewModelAction()
}
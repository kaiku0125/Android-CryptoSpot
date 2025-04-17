package com.kaiku.composecomponent.component.grid

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kaiku.composecomponent.component.grid.data.HomeMenuGridBaseItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.burnoutcrew.reorderable.ItemPosition

class HomeMenuReorderGridViewModel(
    private val homeMenuGridBaseItemList: List<HomeMenuGridBaseItem>
) : ViewModel() {

    private val _viewEvent = Channel<List<HomeMenuGridBaseItem>>(Channel.BUFFERED)
    val viewEvent = _viewEvent.receiveAsFlow()

    var menus by mutableStateOf(
        homeMenuGridBaseItemList
    )

    fun moveMenu(from: ItemPosition, to: ItemPosition) {
        menus = menus.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
        _viewEvent.trySend(menus)
    }

    fun isMenuDragEnabled(draggedOver: ItemPosition, dragging: ItemPosition) =
        menus.getOrNull(draggedOver.index)?.isLocked != true

    fun updateList(homeMenuGridBaseItemList: List<HomeMenuGridBaseItem>) {
        menus = homeMenuGridBaseItemList
        _viewEvent.trySend(menus)
    }
}
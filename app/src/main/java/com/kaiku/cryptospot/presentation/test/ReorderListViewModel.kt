package com.kaiku.cryptospot.presentation.test

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.burnoutcrew.reorderable.ItemPosition

data class ItemData(val title: String, val key: String, val isLocked: Boolean = false)
class ReorderListViewModel : ViewModel() {
    var cats by mutableStateOf(
        List(8) {
            ItemData(
                title = "Cat $it",
                key = "id$it",
                isLocked = it == 8
            )
        }
    )
    var dogs by mutableStateOf(
        List(8) {
            if (it == 7)
                ItemData("Locked", "id$it", true)
            else
                ItemData("Dog $it", "id$it")
        }
    )

    fun moveCat(from: ItemPosition, to: ItemPosition) {
        cats = cats.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }

    fun moveDog(from: ItemPosition, to: ItemPosition) {
        dogs = dogs.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }

    fun isDogDragEnabled(draggedOver: ItemPosition, dragging: ItemPosition) =
        dogs.getOrNull(draggedOver.index)?.isLocked != true
}
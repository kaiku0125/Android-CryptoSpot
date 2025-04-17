package com.kaiku.composecomponent.component.textfield.plusminus.data

import androidx.annotation.StringRes

sealed class PlusMinusErrorEvent {
    data class ToastErrorEvent(
        val msg: String
    ): PlusMinusErrorEvent()

    data class ToastErrorIdEvent(
        @StringRes val resId: Int
    ): PlusMinusErrorEvent()
}
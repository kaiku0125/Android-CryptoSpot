package com.kaiku.composecomponent.component.textfield.plusminus.data

/**
 * PlusMinusActivatorComponent 元件的 viewState
 *
 * @param input 輸入的資料
 * @param display @param input該如何顯示
 * @param isPlusEnable +按鈕enable的狀態
 * @param isMinusEnable -按鈕enable的狀態
 * @param errorEvent 是否在input2output轉換過程中有任何錯誤event
 */
data class PlusMinusActivatorState<T>(
    val input: T? = null,
    val display: String = "",
    val isPlusEnable: Boolean = true,
    val isMinusEnable: Boolean = true,
    val errorEvent: PlusMinusErrorEvent? = null
)
package com.kaiku.composecomponent.component.textfield.plusminus.data



/**
 * 使用 PlusMinusActivatorComponent 元件都必須要實作此策略
 * @param T 輸入輸出運算型態
 *
 * 實作函數的執行順序
 * 1. 透過鍵盤直接輸入 input2output ➔ toViewState
 * 2. 透過加減輸入    calculatePlusMinusResult ➔ input2output ➔ toViewState
 */
interface PlusMinusLogicStrategy<T> {

    // 實作+-邏輯
    fun calculatePlusMinusResult(isPlus: Boolean, value: T?): T?

    // 主要將外部的輸入 ➔ 輸出轉換邏輯 (預設輸入＝輸出)
    fun input2output(value: T?): Pair<T?, PlusMinusErrorEvent?> = (value to null)

    // 實作將輸入強制轉換後的值以Text的形式呈現 (顯示的方式)
    fun toDisplay(value: T?): String

    // 實作+按鈕enable的時機
    fun isPlusEnable(value: T?): Boolean = true

    // 實作-按鈕enable的時機
    fun isMinusEnable(value: T?): Boolean = true

    /**
     * 預設元件顯示流程: 將 外部(ScreenViewModel) 所關心的輸入值，轉換成加減元件的 viewState
     *
     * 若輸入（value)為空，則將鍵盤恢復預設值，反之則進行邏輯運算
     * 輸入的值可能為 鍵盤直接輸入 ||經過加減運算 後的結果
     */
    fun toViewState(value: T?): PlusMinusActivatorState<T> {
        val vs = if (value == null) {
            PlusMinusActivatorState()
        } else {
            val (newValue, errorEvent) = input2output(value)
            PlusMinusActivatorState(
                input = newValue,
                display = toDisplay(newValue),
                isPlusEnable = isPlusEnable(newValue),
                isMinusEnable = isMinusEnable(newValue),
                errorEvent = errorEvent
            )
        }
        return vs
    }
}
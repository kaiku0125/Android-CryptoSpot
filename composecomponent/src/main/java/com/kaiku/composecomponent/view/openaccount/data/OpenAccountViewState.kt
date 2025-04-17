package com.kaiku.composecomponent.view.openaccount.data

/**
 * 開戶畫面的狀態
 *
 * @param title 開戶畫面的標題
 * @param leftText 開戶畫面的左邊文字
 * @param midText 開戶畫面的中間文字
 * @param rightText 開戶畫面的右邊文字
 * @param openAccountMainPicUrl 主開戶畫面的圖片網址
 * @param openAccountTopButtonUrl 開戶畫面的上方按鈕網址
 * @param openAccountBottomButtonUrl 開戶畫面的下方按鈕網址
 */
data class OpenAccountViewState(
    val title: String = "",
    val leftText: String = "",
    val midText: String = "",
    val rightText: String = "",
    val openAccountMainPicUrl: String = "",
    val openAccountTopButtonUrl: String = "",
    val openAccountBottomButtonUrl: String = ""
)

enum class OpenAccountClickArea {
    TOP_BUTTON,
    BOTTOM_BUTTON,
    LEFT_TEXT,
    MID_TEXT,
    RIGHT_TEXT
}

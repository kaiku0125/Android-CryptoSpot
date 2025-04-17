package com.kaiku.composecomponent.view.personalaccount.data

import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.view.personalaccount.data.PersonalAccountViewState.Companion.TEXT_CALCULATING
import com.kaiku.composecomponent.view.personalaccount.data.PersonalAccountViewState.Companion.TEXT_NONE

/**
 * 首頁帳戶卡片狀態
 *
 * @property isLoading 是否載入中
 * @property isVisible 是否隱藏
 * @property isVisitor 是否為訪客
 * @property accountName 證券帳號
 * @property feeRate 手續費
 * @property feeUnit 手續費單位
 * @property minimum 低消
 * @property minimumUnit 低消單位
 * @property remunerationValue 報酬率百分比
 * @property remunerationColor 報酬率顏色
 * @property summaryList 報酬率右方帳戶概覽item
 * @property linkList 帳戶卡片下方有鏈結之item
 */
data class PersonalAccountViewState(
    val isLoading: Boolean = false,
    val isVisible: Boolean = true,
    val isVisitor: Boolean = false,
    val accountName: String = "",
    val feeRate: String = "",
    val feeUnit: String = "",
    val minimum: String = "",
    val minimumUnit: String = "",
    val remunerationValue: Float = 0f,
    val remunerationColor: Color = Color.White,
    val summaryList: List<InfoState> = emptyList(),
    val linkList: List<InfoState> = emptyList()
) {
    companion object {
        const val TEXT_NONE = "--"
        const val TEXT_INVISIBLE = "＊＊＊"
        const val TEXT_INVISIBLE_LONG = "＊＊＊＊＊＊"
        const val TEXT_CALCULATING = "計算中"
    }
}

data class InfoState(
    val title: String,
    val info: String,
    val color: Color,
    val action: PersonalAccountViewAction? = null
) {
    companion object {
        fun create(
            title: String,
            action: PersonalAccountViewAction? = null
        ): InfoState = DEFAULT.copy(
            title = title,
            action = action
        )

        val DEFAULT = InfoState(
            title = "",
            info = TEXT_CALCULATING,
            color = Color.White
        )

        val VISITOR = InfoState(
            title = "",
            info = TEXT_NONE,
            color = Color.White
        )
    }
}



package com.kaiku.composecomponent.view.menu.menuallfunction.data

import androidx.annotation.DrawableRes
import com.kaiku.composecomponent.component.grid.data.HomeMenuGridBaseItem

data class MenuAllFunctionViewState(
    val homeMenuGroupCompleteItemList: List<HomeMenuGroupLocalItemStructure> = emptyList(), // 從remote config 拿到的資料，用來建立Pager選項以及選項內詳細項目
    val homeMenuGridBaseItemList: List<HomeMenuGridBaseItem> = emptyList(), // 顯示在畫面上可以滑動的資料
    val isLocked: Boolean = true, // 是否為編輯模式
    val canBeSaved: Boolean = false, // 是否可以儲存
)

/**
 * 所有功能畫面上由 firebase 去控的字串
 */
data class MenuAllFunctionStringRes(
    val menuAllFunctionTitle: String = "所有功能",
    val menuAllFunctionModify: String = "編輯釘選",
    val menuAllFunctionSave: String = "儲存",
) {
    companion object {
        val DEFAULT = MenuAllFunctionStringRes()
    }
}

data class HomeMenuGroupLocalItemStructure(
    val id: String,
    val name: String,
    val items: List<MenuGroupStructure>
)

data class MenuGroupStructure(
    val menuIconType: MenuIconType,
    val iconUrl: String,
    val key: String,
    val title: String,
    @DrawableRes val iconResId: Int,
)

const val BADGE_TYPE_INVISIBLE = 0
const val BADGE_TYPE_NEW = 1
const val BADGE_TYPE_UNRECEIVED = 2

package com.kaiku.composecomponent.utils.navigation.data

/**
 *  @property Back : 將目前 screen 移除 stack，並返回前一個畫面
 *
 *  @property To : 導航到指定目標
 *
 *  @property Replace : 替換當前 screen
 *
 *  @property OffAllTo : 清空導航stack并導航到指定目的地
 */
sealed class NavIntent{

    data class Back(
        val route: String? = null,
        val inclusive: Boolean = false,
    ) : NavIntent()


    data class To(
        val route: String,
        val popUpToRoute: String? = null,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false,
    ) : NavIntent()


    data class Replace(
        val route: String,
        val isSingleTop: Boolean = false,
    ) : NavIntent()


    data class OffAllTo(
        val route: String,
    ) : NavIntent()


}

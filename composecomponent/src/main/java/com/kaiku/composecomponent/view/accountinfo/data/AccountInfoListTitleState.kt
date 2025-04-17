package com.kaiku.composecomponent.view.accountinfo.data

data class AccountInfoListTitleState(
    val weight: Float,
    val title: String,
    val filterState: FilterState? = null
) {
    companion object {
        val PREVIEW = listOf(
            AccountInfoListTitleState(
                weight = 40f,
                title = "時間",
                filterState = FilterState.DESCEND
            ),
            AccountInfoListTitleState(
                weight = 72f,
                title = "類別",
                filterState = FilterState.DEFAULT
            ),
            AccountInfoListTitleState(
                weight = 48f,
                title = "狀態",
                filterState = FilterState.DEFAULT
            ),
            AccountInfoListTitleState(
                weight = 90f,
                title = "標的",
                filterState = FilterState.DEFAULT
            ),
            AccountInfoListTitleState(
                weight = 63f,
                title = "委託股數\n" +
                        "委託價格",
                filterState = null
            ),

        )
    }
}

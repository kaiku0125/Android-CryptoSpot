package com.kaiku.cryptospot.customView.tab.data

import androidx.annotation.StringRes
import com.kaiku.cryptospot.R
import com.kaiku.cryptospot.customView.BasicType

sealed class BadgeTabType(
    override val position: Int,
    @StringRes override val description: Int,
    override val tag: String,
    val isBadgeLight: Boolean,
    val isEnable : Boolean
) : BasicType {

    object TabOne: BadgeTabType(
        position = 0,
        description = R.string.tab_item_one,
        tag = "現",
        isBadgeLight = true,
        isEnable = true
    )

    object TabTwo: BadgeTabType(
        position = 1,
        description = R.string.tab_item_two,
        tag = "資",
        isBadgeLight = false,
        isEnable = true
    )

    object TabThree: BadgeTabType(
        position = 2,
        description = R.string.tab_item_three,
        tag = "券",
        isBadgeLight = true,
        isEnable = true
    )

    object TabFour : BadgeTabType(
        position = 3,
        description = R.string.tab_item_four,
        tag = "沖",
        isBadgeLight = false,
        isEnable = false
    )


    companion object {

        fun getAll(): List<BadgeTabType> {
            return listOf(
                TabOne,
                TabTwo,
                TabThree,
                TabFour,
            )
        }
    }
}

package com.kaiku.cryptospot.customView.dialog.data

import androidx.annotation.StringRes
import com.kaiku.cryptospot.R
import com.kaiku.cryptospot.customView.BasicType

sealed class FlashRatioType(
    override val position: Int,
    @StringRes override val description: Int,
    override val tag: String
) : BasicType {

    object RatioNormalOrder : FlashRatioType(
        position = 0,
        description = R.string.ratio_item_normal_order,
        tag = RATIO_NORMAL_ORDER
    )

    object RatioConditionOrder : FlashRatioType(
        position = 1,
        description = R.string.ratio_item_condition_order,
        tag = RATIO_CONDITION_ORDER
    )

    companion object {
        private const val RATIO_NORMAL_ORDER = "ratio_normal_order_item"
        private const val RATIO_CONDITION_ORDER = "ratio_condition_order_item"

        fun getAll(): List<FlashRatioType> {
            return listOf(
                RatioNormalOrder,
                RatioConditionOrder
            )
        }
    }

}
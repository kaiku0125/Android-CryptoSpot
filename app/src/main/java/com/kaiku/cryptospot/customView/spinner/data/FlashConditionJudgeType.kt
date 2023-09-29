package com.kaiku.cryptospot.customView.spinner.data

import androidx.annotation.StringRes
import com.kaiku.cryptospot.R
import com.kaiku.cryptospot.customView.BasicType

sealed class FlashConditionJudgeType(
    override val position: Int,
    @StringRes override val description: Int,
    override val tag: String
) : BasicType {

    object LagerEqualThan : FlashConditionJudgeType(
        position = 0,
        description = R.string.spinner_item_larger_equal_than,
        tag = LARGER_EQUAL_THAN
    )

    object LessEqualThan : FlashConditionJudgeType(
        position = 1,
        description = R.string.spinner_item_larger_equal_than,
        tag = LESS_EQUAL_THAN
    )

    companion object {
        private const val LARGER_EQUAL_THAN = "larger_equal_than"
        private const val LESS_EQUAL_THAN = "less_equal_than"

        fun getAll(): List<FlashConditionJudgeType> {
            return listOf(
                LagerEqualThan,
                LessEqualThan,
            )
        }
    }
}
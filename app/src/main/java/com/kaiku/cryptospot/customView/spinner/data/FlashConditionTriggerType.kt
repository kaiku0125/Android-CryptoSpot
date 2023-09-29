package com.kaiku.cryptospot.customView.spinner.data

import androidx.annotation.StringRes
import com.kaiku.cryptospot.R
import com.kaiku.cryptospot.customView.BasicType

sealed class FlashConditionTriggerType(
    override val position: Int,
    @StringRes override val description: Int,
    override val tag: String
) : BasicType {

    object DealPrice : FlashConditionTriggerType(
        position = 0,
        description = R.string.spinner_item_flash_deal_price,
        tag = DEAL_PRICE
    )

    object BuyPrice : FlashConditionTriggerType(
        position = 1,
        description = R.string.spinner_item_flash_buy_price,
        tag = BUY_PRICE
    )

    companion object {
        private const val DEAL_PRICE = "deal_price_item"
        private const val BUY_PRICE = "buy_price_item"

        fun getAll(): List<FlashConditionTriggerType> {
            return listOf(
                DealPrice,
                BuyPrice,
            )
        }
    }
}

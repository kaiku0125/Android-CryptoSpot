package com.kaiku.composecomponent.view.accountinfo.data

import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.color_39b54a

data class AccountInfoSummaryState(
    val fee: String,
    val feeColor: Color = Color.White,
    val tax: String,
    val taxColor: Color = Color.White,
    val settlement: String,
    val settlementColor: Color,
    val unit: String = "元"
) {
    companion object {
        val PREVIEW = AccountInfoSummaryState(
            fee = "54,879,487",
            tax = "9,487",
            settlement = "-1,541",
            settlementColor = color_39b54a
        )
    }
}

package com.kaiku.cryptospot.customView.tab.data

import androidx.annotation.StringRes
import com.kaiku.cryptospot.R

sealed class CryptoTabType(
    override val position: Int,
    @StringRes override val description: Int,
    override val tag: String
) : TabType{

    object TabBTC: CryptoTabType(
        position = 0,
        description = R.string.tab_item_btc,
        tag = BTC
    )

    object TabETH: CryptoTabType(
        position = 1,
        description = R.string.tab_item_eth,
        tag = ETH
    )

    object TabADA: CryptoTabType(
        position = 2,
        description = R.string.tab_item_ada,
        tag = ADA
    )



    companion object {
        private const val BTC = "tab_btc_item"
        private const val ETH = "tab_eth_item"
        private const val ADA = "tab_ada_item"


        fun getAll(): List<CryptoTabType> {
            return listOf(
                TabBTC,
                TabETH,
                TabADA
            )
        }
    }
}

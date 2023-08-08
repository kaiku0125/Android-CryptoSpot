package com.kaiku.cryptospot.customView.spinner.data

import androidx.annotation.StringRes
import com.kaiku.cryptospot.R

sealed class CryptoSpinnerType(
    override val position: Int,
    @StringRes override val description: Int,
    override val tag: String
) : SpinnerType{

    object SpinnerBTC : CryptoSpinnerType(
        position = 0,
        description = R.string.spinner_item_btc,
        tag = BTC
    )

    object SpinnerETH : CryptoSpinnerType(
        position = 1,
        description = R.string.spinner_item_eth,
        tag = ETH
    )

    object SpinnerADA : CryptoSpinnerType(
        position = 2,
        description = R.string.spinner_item_ada,
        tag = ADA
    )



    companion object {
        private const val BTC = "spinner_btc_item"
        private const val ETH = "spinner_eth_item"
        private const val ADA = "spinner_ada_item"


        fun getAll(): List<CryptoSpinnerType> {
            return listOf(
                SpinnerBTC,
                SpinnerETH,
                SpinnerADA
            )
        }
    }

}

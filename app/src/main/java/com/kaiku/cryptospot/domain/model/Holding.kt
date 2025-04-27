package com.kaiku.cryptospot.domain.model

import com.kaiku.cryptospot.data.db.holding.UserHoldingEntity

data class Holding(
    val symbol: String,
    val amount: Double,
    val cost: Double
) {
    fun toEntity(): UserHoldingEntity = UserHoldingEntity(
        symbol = this.symbol,
        amount = this.amount,
        cost = this.cost
    )
}

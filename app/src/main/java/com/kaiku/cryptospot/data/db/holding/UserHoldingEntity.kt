package com.kaiku.cryptospot.data.db.holding

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kaiku.cryptospot.domain.model.Holding

@Entity(tableName = UserHoldingEntity.TABLE_NAME)
data class UserHoldingEntity(
    @PrimaryKey
    val symbol: String,
    val amount: Double,
    val cost: Double
) {
    fun toUsage(): Holding = Holding(
        symbol = this.symbol,
        amount = this.amount,
        cost = this.cost
    )

    companion object {
        const val TABLE_NAME = "user_holdings_info"
    }
}

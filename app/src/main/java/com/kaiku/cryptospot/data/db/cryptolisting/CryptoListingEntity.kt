package com.kaiku.cryptospot.data.db.cryptolisting

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CryptoListingEntity.TABLE_NAME)
data class CryptoListingEntity(
    @PrimaryKey
    val rank: Int,
    val id: Int,
    val symbol: String,
    val price: Double
) {
    companion object {
        const val TABLE_NAME = "crypto_listing"
    }
}

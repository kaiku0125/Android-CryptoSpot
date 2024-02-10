package com.kaiku.cryptospot.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CryptoListingEntity(
    @PrimaryKey
    val rank: Int,
    val id: Int,
    val symbol: String,
    val price: Double
)

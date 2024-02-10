package com.kaiku.cryptospot.data.remote.dto.crypto_list

import com.google.gson.annotations.SerializedName
import com.kaiku.cryptospot.data.db.CryptoListingEntity
import com.kaiku.cryptospot.domain.model.CryptoListingData
import timber.log.Timber

data class CryptoListingDataDto(
    val id: Int,
    val name: String,
    val symbol: String,
    val cmc_rank: Int,
    val quote: Quote
)

data class Quote(
    @SerializedName("USD")
    val usd: QuoteInfo,
    @SerializedName("BTC")
    val btc: QuoteInfo
)

data class QuoteInfo(
    val price: Double,
//    @SerializedName("volume_24h")
//    val volume24h: Double,
//    @SerializedName("volume_change_24h")
//    val volumeChange24h: Double,
//    @SerializedName("percent_change_1h")
//    val percentChange1h: Double,
//    @SerializedName("percent_change_24h")
//    val percentChange24h: Double,
//    @SerializedName("percent_change_7d")
//    val percentChange7d: Double,
//    @SerializedName("market_cap")
//    val marketCap: Double,
//    @SerializedName("market_cap_dominance")
//    val marketCapDominance: Double,
//    @SerializedName("fully_diluted_market_cap")
//    val fullyDilutedMarketCap: Double,
//    @SerializedName("last_updated")
//    val lastUpdated: String
)


fun CryptoListingDataDto.toPoint() : CryptoListingData{
    return CryptoListingData(
        rank = cmc_rank,
        id = id,
        symbol = symbol,
        price = quote.usd.price
    )
}

fun CryptoListingDataDto.toEntity() : CryptoListingEntity {
    return CryptoListingEntity(
        rank = cmc_rank,
        id = id,
        symbol = symbol,
        price = quote.usd.price
    )
}

fun CryptoListingEntity.toData() : CryptoListingData {
    return CryptoListingData(
        rank = rank,
        id = id,
        symbol = symbol,
        price = price
    )
}



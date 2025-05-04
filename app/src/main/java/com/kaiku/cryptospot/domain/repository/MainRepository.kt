package com.kaiku.cryptospot.domain.repository

import com.kaiku.cryptospot.data.db.cryptolisting.CryptoListingEntity
import com.kaiku.cryptospot.data.remote.dto.crypto_list.CryptoListResponse
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun requestCryptoList(
        start: Int,
        limit: Int,
    ) : Result<CryptoListResponse>

    suspend fun isCryptoListCacheExpired(): Boolean

    suspend fun updateAllCryptoList(entities: List<CryptoListingEntity>)

}
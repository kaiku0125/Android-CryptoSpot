package com.kaiku.cryptospot.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.withTransaction
import com.kaiku.cryptospot.data.db.CryptoSpotDatabase
import com.kaiku.cryptospot.data.db.cachetime.CacheTimeEntity
import com.kaiku.cryptospot.data.db.cryptolisting.CryptoListingEntity
import com.kaiku.cryptospot.data.remote.CoinMarketCapApi
import com.kaiku.cryptospot.data.remote.dto.crypto_list.CryptoListResponse
import com.kaiku.cryptospot.domain.repository.MainRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

class MainRepositoryImpl(
    private val api: CoinMarketCapApi,
    private val db: CryptoSpotDatabase,
) : MainRepository {

    override suspend fun requestCryptoList(start: Int, limit: Int): Result<CryptoListResponse> {
        return withTimeout(5000L) {
            runCatching {
                api.getCryptoListings(
                    start = start,
                    limit = limit
                )
            }
        }
    }

    override suspend fun isCryptoListCacheExpired(): Boolean {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val cacheTime = db.cacheTimeDao.getByTag("crypto_list").firstOrNull()?.expiredTime ?: 0L

        return System.currentTimeMillis() - cacheTime > cacheTimeout
    }

    override suspend fun updateAllCryptoList(entities: List<CryptoListingEntity>) {
        db.withTransaction {
            db.dao.deleteAll()
            db.dao.upsertAll(entities)
            db.cacheTimeDao.upsert(
                CacheTimeEntity(
                    tag = "crypto_list",
                    expiredTime = System.currentTimeMillis()
                )
            )
        }
    }

    override fun getPagerByQuery(query: String): Pager<Int, CryptoListingEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                initialLoadSize = 50
            ),
            pagingSourceFactory = { db.dao.pagingSource(query) }
        )
    }
}
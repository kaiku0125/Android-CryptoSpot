package com.kaiku.cryptospot.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kaiku.cryptospot.data.db.CryptoSpotDatabase
import com.kaiku.cryptospot.data.db.cachetime.CacheTimeEntity
import com.kaiku.cryptospot.data.db.cryptolisting.CryptoListingEntity
import com.kaiku.cryptospot.data.remote.CoinMarketCapApi
import com.kaiku.cryptospot.data.remote.dto.crypto_list.toEntity
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class CryptoListingMediator(
    private val db: CryptoSpotDatabase,
    private val api: CoinMarketCapApi
) : RemoteMediator<Int, CryptoListingEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val cacheTime = db.cacheTimeDao.getByTag("crypto_list").firstOrNull()?.expiredTime ?: 0L
        return if (System.currentTimeMillis() - cacheTime <= cacheTimeout)
        {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            Timber.tag("wtf").e("SKIP_INITIAL_REFRESH")
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            Timber.tag("wtf").e("LAUNCH_INITIAL_REFRESH")
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CryptoListingEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        lastItem.rank + 1
                    }
                }
            }

            Timber.tag("wtf").e("start = $loadKey, limit = ${state.config.pageSize}")
            val response = api.getCryptoListings(start = loadKey, limit = state.config.pageSize)
            Timber.tag("wtf").e("response ➔ ${response.data}")

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.dao.deleteAll()
                }

                val cryptoListingEntities = response.data.map {
                    it.toEntity()
                }
                db.dao.upsertAll(cryptoListingEntities)
                db.cacheTimeDao.upsert(
                    CacheTimeEntity(
                        tag = "crypto_list",
                        expiredTime = System.currentTimeMillis()
                    )
                )
            }

            MediatorResult.Success(endOfPaginationReached = response.data.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
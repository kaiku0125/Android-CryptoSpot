package com.kaiku.cryptospot.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kaiku.cryptospot.data.db.CryptoListingDatabase
import com.kaiku.cryptospot.data.db.CryptoListingEntity
import com.kaiku.cryptospot.data.remote.CoinMarketCapApi
import com.kaiku.cryptospot.data.remote.dto.crypto_list.toEntity
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOError
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CryptoListingMediator(
    private val db: CryptoListingDatabase,
    private val api: CoinMarketCapApi
) : RemoteMediator<Int, CryptoListingEntity>() {

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

//            Timber.tag("wtf").e("start = $loadKey, limit = ${state.config.pageSize}")
            val response = api.getCryptoListings(start = loadKey, limit = state.config.pageSize)
//            Timber.tag("wtf").e("response ➔ ${response.data}")

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.dao.deleteAll()
                }

                val cryptoListingEntities = response.data.map {
                    it.toEntity()
                }
                db.dao.upsertAll(cryptoListingEntities)
            }

            MediatorResult.Success(endOfPaginationReached = response.data.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
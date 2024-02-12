package com.kaiku.cryptospot.data.db.cryptolisting

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kaiku.cryptospot.data.remote.CoinMarketCapApi
import com.kaiku.cryptospot.data.remote.dto.crypto_list.toEntity

@Dao
interface CryptoListingDao {

    @Upsert
    suspend fun upsertAll(cryptos: List<CryptoListingEntity>)

    @Query("SELECT * FROM CryptoListingEntity")
    fun pagingSource(): PagingSource<Int, CryptoListingEntity>

    @Query("DELETE FROM CryptoListingEntity")
    suspend fun deleteAll()
}

const val NETWORK_PAGE_SIZE = 25
private const val INITIAL_LOAD_SIZE = 2
class CryptoListPagingSource(
    private val api: CoinMarketCapApi,
) : PagingSource<Int, CryptoListingEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CryptoListingEntity> {
        // Start refresh at position 1 if undefined.
        val position = params.key ?: INITIAL_LOAD_SIZE
        val offset = if (params.key != null) ((position - 1) * NETWORK_PAGE_SIZE) + 1 else INITIAL_LOAD_SIZE
        return try {
            val response = api.getCryptoListings(start = offset, limit = params.loadSize).data
            val data = response.map {
                it.toEntity()
            }
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = data,
                prevKey = null, // Only paging forward.
                // assume that if a full page is not loaded, that means the end of the data
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CryptoListingEntity>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return null
    }
}
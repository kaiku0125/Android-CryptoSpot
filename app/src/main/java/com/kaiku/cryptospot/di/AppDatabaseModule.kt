package com.kaiku.cryptospot.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.kaiku.cryptospot.data.CryptoListingMediator
import com.kaiku.cryptospot.data.db.CryptoSpotDatabase
import com.kaiku.cryptospot.data.db.UserHoldingDatabase
import com.kaiku.cryptospot.data.db.cryptolisting.CryptoListingEntity
import com.kaiku.cryptospot.data.remote.CoinMarketCapApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@OptIn(ExperimentalPagingApi::class)
val appDatabaseModule = module {
    single<CryptoSpotDatabase> {
        Room.databaseBuilder(
            androidContext(),
            CryptoSpotDatabase::class.java,
            "crypto_listing_db"
        ).build()
    }

    single<Pager<Int, CryptoListingEntity>> {
        val db: CryptoSpotDatabase = get()
        val api: CoinMarketCapApi = get()

        Pager(
            config = PagingConfig(
                pageSize = 25,
                initialLoadSize = 50
            ),
            remoteMediator = CryptoListingMediator(
                db = db,
                api = api
            ),
            pagingSourceFactory = {
                db.dao.pagingSource()
            }
        )
    }

    single {
        val database = get<CryptoSpotDatabase>()
        database.dao
    }

    single<UserHoldingDatabase> {
        UserHoldingDatabase.getInstance(androidContext())
    }

    single {
        val userHoldingDatabase = get<UserHoldingDatabase>()
        userHoldingDatabase.userHoldingDao
    }
}
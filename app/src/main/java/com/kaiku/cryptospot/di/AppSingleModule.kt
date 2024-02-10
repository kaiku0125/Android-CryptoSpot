package com.kaiku.cryptospot.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.kaiku.cryptospot.data.CryptoListingMediator
import com.kaiku.cryptospot.data.db.CryptoListingDatabase
import com.kaiku.cryptospot.data.db.CryptoListingEntity
import com.kaiku.cryptospot.data.prefs.Prefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@OptIn(ExperimentalPagingApi::class)
val appSingleModule = module {
    single<Prefs>{
        Prefs(
            context = androidContext()
        )
    }
    single<CryptoListingDatabase> {
        Room.databaseBuilder(
            androidContext(),
            CryptoListingDatabase::class.java,
            "crypto_listing_db"
        ).build()
    }
    single<Pager<Int, CryptoListingEntity>> {
        val db = get<CryptoListingDatabase>()
        Pager(
            config = PagingConfig(
                pageSize = 25,
                initialLoadSize = 50
            ),
            remoteMediator = CryptoListingMediator(
                db = db,
                api = get()
            ),
            pagingSourceFactory = {
                db.dao.pagingSource()
            }
        )
    }
}
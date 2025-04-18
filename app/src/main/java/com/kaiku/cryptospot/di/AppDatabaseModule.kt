package com.kaiku.cryptospot.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.kaiku.cryptospot.data.CryptoListingMediator
import com.kaiku.cryptospot.data.db.CryptoSpotDatabase
import com.kaiku.cryptospot.data.db.cryptolisting.CryptoListingEntity
import com.kaiku.cryptospot.data.remote.CoinMarketCapApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@OptIn(ExperimentalPagingApi::class)
val appDatabaseModule = module {

}
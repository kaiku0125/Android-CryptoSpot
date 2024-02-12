package com.kaiku.cryptospot.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kaiku.cryptospot.data.db.cachetime.CacheTimeDao
import com.kaiku.cryptospot.data.db.cachetime.CacheTimeEntity
import com.kaiku.cryptospot.data.db.cryptolisting.CryptoListingDao
import com.kaiku.cryptospot.data.db.cryptolisting.CryptoListingEntity

@Database(
    entities = [
        CryptoListingEntity::class,
        CacheTimeEntity::class
    ],
    version = 1
)
abstract class CryptoSpotDatabase : RoomDatabase() {

    abstract val dao: CryptoListingDao

    abstract val cacheTimeDao: CacheTimeDao
}
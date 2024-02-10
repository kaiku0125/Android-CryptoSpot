package com.kaiku.cryptospot.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kaiku.cryptospot.data.db.CryptoListingDao
import com.kaiku.cryptospot.data.db.CryptoListingEntity

@Database(
    entities = [CryptoListingEntity::class],
    version = 1
)
abstract class CryptoListingDatabase : RoomDatabase(){

    abstract val dao: CryptoListingDao
}
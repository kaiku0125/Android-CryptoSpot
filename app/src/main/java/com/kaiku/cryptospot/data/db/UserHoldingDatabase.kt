package com.kaiku.cryptospot.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kaiku.cryptospot.data.db.holding.UserHoldingDao
import com.kaiku.cryptospot.data.db.holding.UserHoldingEntity

@Database(
    entities = [
        UserHoldingEntity::class
    ],
    version = 1
)
abstract class UserHoldingDatabase  : RoomDatabase(){

    abstract val userHoldingDao: UserHoldingDao

    companion object {
        private const val DATABASE_NAME = "user_holding_db"

        @Volatile
        private var INSTANCE: UserHoldingDatabase? = null

        fun getInstance(context: Context): UserHoldingDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context, UserHoldingDatabase::class.java, DATABASE_NAME)
//                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}
package com.kaiku.cryptospot.data.db.holding

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserHoldingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHolding(holding: UserHoldingEntity)

    @Update
    suspend fun updateHolding(holding: UserHoldingEntity)

    @Delete
    suspend fun deleteHolding(holding: UserHoldingEntity)

    @Query("SELECT * FROM ${UserHoldingEntity.TABLE_NAME} WHERE symbol = :symbol LIMIT 1")
    suspend fun getHoldingBySymbol(symbol: String): UserHoldingEntity?


    @Query("SELECT * FROM ${UserHoldingEntity.TABLE_NAME}")
    fun getAllHoldings(): Flow<List<UserHoldingEntity>>

}
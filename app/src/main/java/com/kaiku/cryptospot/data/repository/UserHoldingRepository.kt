package com.kaiku.cryptospot.data.repository

import androidx.room.withTransaction
import com.kaiku.cryptospot.data.db.UserHoldingDatabase
import com.kaiku.cryptospot.data.db.holding.UserHoldingEntity
import kotlinx.coroutines.flow.Flow

class UserHoldingRepository(
    private val db: UserHoldingDatabase
) {
    private val dao = db.userHoldingDao

    suspend fun add(holding: UserHoldingEntity) {
        dao.insertHolding(holding = holding)
    }

    suspend fun updateHolding(holding: UserHoldingEntity) {
        dao.updateHolding(holding)
    }

    suspend fun deleteHolding(holding: UserHoldingEntity) {
        dao.deleteHolding(holding)
    }

    fun getAllHoldings(): Flow<List<UserHoldingEntity>> {
        return dao.getAllHoldings()
    }

    suspend fun getHoldingBySymbol(symbol: String): UserHoldingEntity? {
        return dao.getHoldingBySymbol(symbol)
    }
}
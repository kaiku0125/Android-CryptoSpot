package com.kaiku.cryptospot.data.db.cachetime

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CacheTimeDao {

    @Upsert
    suspend fun upsert(cacheTimeEntity: CacheTimeEntity)

    @Query("SELECT * FROM ${CacheTimeEntity.TABLE_NAME} WHERE tag IN (:tags)")
    suspend fun getByTag(vararg tags: String): List<CacheTimeEntity>

    @Query("DELETE FROM ${CacheTimeEntity.TABLE_NAME}")
    suspend fun deleteAll(): Int

    @Query("DELETE FROM ${CacheTimeEntity.TABLE_NAME} WHERE tag = :tag")
    suspend fun delete(tag: String): Int
}
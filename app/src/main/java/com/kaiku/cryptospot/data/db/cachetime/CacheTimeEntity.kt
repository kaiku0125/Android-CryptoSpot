package com.kaiku.cryptospot.data.db.cachetime

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CacheTimeEntity.TABLE_NAME)
data class CacheTimeEntity(
    @PrimaryKey
    @ColumnInfo(name = "tag")
    val tag: String,
    val expiredTime: Long
) {
    companion object {
        const val TABLE_NAME = "cache_time"
    }
}


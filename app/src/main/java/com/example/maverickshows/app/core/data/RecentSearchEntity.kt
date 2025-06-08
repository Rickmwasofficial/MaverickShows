package com.example.maverickshows.app.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Recent Search")
data class RecentSearchEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String = "0",
    val title: String,
    val type: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)

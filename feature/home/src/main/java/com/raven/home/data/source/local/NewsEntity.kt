package com.raven.home.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "news", indices = [Index(value = ["newsId"], unique = true)])
data class NewsEntity(
    @PrimaryKey @ColumnInfo(name = "newsId") val newsId: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "subtitle") val subtitle: String,
    @ColumnInfo(name = "copyright") val copyright: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "published_date") val publishedDate: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String
)

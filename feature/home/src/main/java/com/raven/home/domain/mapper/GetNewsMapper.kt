package com.raven.home.domain.mapper

import com.raven.home.data.entities.NewsData
import com.raven.home.data.entities.ResponseNewsData
import com.raven.home.data.source.local.NewsEntity
import com.raven.home.domain.models.ItemNews
import com.raven.home.domain.models.NewsDomain


fun ResponseNewsData.toNewsDomain(): NewsDomain =
    NewsDomain(
        items = newsDataList?.let { news ->
            news.flatMap {
                arrayListOf(it.toNewsDomain())
            }
        } ?: arrayListOf()
    )

fun NewsData.toNewsDomain(): ItemNews = ItemNews(
    title = title ?: "",
    subtitle = abstract ?: "",
    copyright = media?.firstOrNull()?.copyright ?: "",
    urlImage = media?.firstOrNull()?.mediaMetadata?.get(2)?.url ?: "",
    id = id ?: 0,
    author = byline ?: "",
    publishedDate = publishedDate ?: ""
)

fun ItemNews.toNewsEntity(): NewsEntity = NewsEntity(
    newsId = id,
    title = title,
    subtitle = subtitle,
    copyright = copyright,
    author = author,
    publishedDate = publishedDate,
    imageUrl = urlImage
)

fun NewsEntity.toDomain(): ItemNews = ItemNews(
    id = newsId,
    title = title,
    subtitle = subtitle,
    copyright = copyright,
    urlImage = imageUrl,
    author = author,
    publishedDate = publishedDate
)
package com.raven.home.domain.mapper

import com.raven.home.data.entities.NewsData
import com.raven.home.data.entities.ResponseNewsData
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
    copyright = media?.first()?.copyright ?: "",
    urlImage = url ?: ""
)

package com.raven.home.domain.models

import java.io.Serializable

data class NewsDomain(
    val items: List<ItemNews>
)

data class ItemNews(
    val id: Long,
    val title: String,
    val subtitle: String,
    val copyright: String,
    val urlImage: String,
    val author: String,
    val publishedDate: String
):Serializable
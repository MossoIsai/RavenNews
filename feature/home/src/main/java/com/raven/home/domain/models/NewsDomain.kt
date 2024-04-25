package com.raven.home.domain.models

data class NewsDomain(
    val items: List<ItemNews>,

)
data class ItemNews(
    val title: String,
    val subtitle: String,
    val copyright: String,
    val urlImage: String
)

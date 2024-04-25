package com.raven.home.data.entities

import com.google.gson.annotations.SerializedName


data class ResponseNewsData(
    val status: String?,
    val copyright: String?,
    @SerializedName("num_results") val numResults: Int?,
    @SerializedName("results") val newsDataList: List<NewsData>?
)

data class NewsData(
    val uri: String?,
    val url: String?,
    val id: Long?,
    @SerializedName("asset_id") val assetId: Long?,
    val source: String?,
    @SerializedName("published_date") val publishedDate: String?,
    val section: String?,
    val subsection: String?,
    @SerializedName("nytdsection") val nytdSection: String?,
    @SerializedName("adx_keywords") val adxKeywords: String?,
    val column: String? = null,
    val byline: String?,
    val type: String?,
    val title: String?,
    val abstract: String?,
    @SerializedName("des_facet") val desFacet: List<String>?,
    @SerializedName("org_facet") val orgFacet: List<String>?,
    @SerializedName("per_facet") val perFacet: List<String>?,
    @SerializedName("geo_facet") val geoFacet: List<String>?,
    val media: List<MediaData>?,
    @SerializedName("eta_id") val etaId: Int?
)

data class MediaData(
    val type: String?,
    val subtype: String?,
    val caption: String?,
    val copyright: String?,
    @SerializedName("approved_for_syndication") val approvedForSyndication: Int?,
    @SerializedName("media-metadata") val mediaMetadata: List<MediaMetadataData>?,
)

data class MediaMetadataData(
    val url: String?,
    val format: String?,
    val height: Int?,
    val width: Int?
)

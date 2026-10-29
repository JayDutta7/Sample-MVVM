package com.sample.mvvm.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) representing a news publisher source (e.g., BBC News, CNN).
 *
 * **Architecture Role:**
 * Used across network responses, publisher selection lists ([com.sample.mvvm.ui.news_sources.NewsSourcesScreen]),
 * and nested inside [Article] models.
 *
 * **Implementation Decisions:**
 * Fields other than [name] are marked nullable to safely accommodate partial source data inside article payloads.
 *
 * @property id Unique string key identifier for the publisher (e.g., "bbc-news").
 * @property name Display name of the news publisher.
 * @property description Short summary of the publisher's editorial scope.
 * @property url Official website homepage URL of the publisher.
 * @property category General category classification (e.g., "technology", "sports").
 * @property language ISO language code used by the publisher.
 * @property country ISO country code where the publisher is based.
 */
data class Source(
    @SerializedName("id")
    val id: String?,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("description")
    val description: String? = null,
    
    @SerializedName("url")
    val url: String? = null,
    
    @SerializedName("category")
    val category: String? = null,
    
    @SerializedName("language")
    val language: String? = null,
    
    @SerializedName("country")
    val country: String? = null
)

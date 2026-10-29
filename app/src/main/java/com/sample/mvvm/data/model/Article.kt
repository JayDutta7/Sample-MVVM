package com.sample.mvvm.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) representing a single news article payload received from the News API.
 *
 * **Architecture Role:**
 * Serves as the primary data model for article items across the data layer (network responses) and presentation layer (Compose UI cards).
 *
 * **Implementation Decisions:**
 * - Uses Gson's `@SerializedName` annotations to map JSON fields strictly while retaining idiomatic Kotlin property naming.
 * - Optional properties such as [author], [description], [urlToImage], and [content] are declared as nullable strings to safely handle incomplete API responses without deserialization crashes.
 *
 * @property source The origin publisher source details for this article.
 * @property author The writer or reporter name, if provided by the publisher.
 * @property title The primary headline text of the news article.
 * @property description A brief summary or excerpt of the article body.
 * @property url The direct canonical Web URL to read the full article.
 * @property urlToImage The remote HTTP image preview thumbnail URL.
 * @property publishedAt The ISO-8601 UTC timestamp string indicating publication time.
 * @property content The truncated preview snippet of article body content.
 */
data class Article(
    @SerializedName("source")
    val source: Source,

    @SerializedName("author")
    val author: String?,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("url")
    val url: String,

    @SerializedName("urlToImage")
    val urlToImage: String?,

    @SerializedName("publishedAt")
    val publishedAt: String,

    @SerializedName("content")
    val content: String?
)

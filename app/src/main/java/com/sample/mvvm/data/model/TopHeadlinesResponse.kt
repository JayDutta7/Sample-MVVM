package com.sample.mvvm.data.model

import com.google.gson.annotations.SerializedName

/**
 * Top-level API envelope response object returned by News API `/v2/top-headlines` and `/v2/everything` endpoints.
 *
 * **Architecture Role:**
 * Decoded by Retrofit/Gson in [com.sample.mvvm.data.api.NetworkService] and processed by repositories to extract article lists.
 *
 * @property status Response status string ("ok" or "error").
 * @property totalResults Total count of matching articles available from the query.
 * @property articles List of returned [Article] items.
 */
data class TopHeadlinesResponse(
    @SerializedName("status")
    val status: String,
    
    @SerializedName("totalResults")
    val totalResults: Int,
    
    @SerializedName("articles")
    val articles: List<Article> = emptyList()
)

package com.sample.mvvm.data.model

import com.google.gson.annotations.SerializedName

/**
 * Top-level API envelope response object returned by the News API `/v2/sources` endpoint.
 *
 * **Architecture Role:**
 * Decoded by Retrofit/Gson in [com.sample.mvvm.data.api.NetworkService.getNewsSources] and unpacked inside [com.sample.mvvm.data.repository.NewsSourcesRepository].
 *
 * @property status Response status string ("ok" or "error").
 * @property sources List of available publisher [Source] items, if successful.
 * @property code Error code string provided when status is "error".
 * @property message Detailed error message string provided when status is "error".
 */
data class SourcesResponse(
    @SerializedName("status")
    val status: String,
    
    @SerializedName("sources")
    val sources: List<Source>? = null,
    
    @SerializedName("code")
    val code: String? = null,
    
    @SerializedName("message")
    val message: String? = null
)

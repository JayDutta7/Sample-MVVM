package com.sample.mvvm.data.api

import com.sample.mvvm.data.model.SourcesResponse
import com.sample.mvvm.data.model.TopHeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit REST API interface defining HTTP endpoints for the News API (v2).
 *
 * **Architecture Role:**
 * Serves as the remote data source boundary. Executed asynchronously using Kotlin `suspend` functions,
 * which Retrofit automatically offloads to background thread pools.
 *
 * **Implementation Decisions:**
 * - Uses `suspend` functions for idiomatic non-blocking coroutine execution.
 * - API authentication (`apiKey`) is managed globally via an OkHttp `Interceptor` in [com.sample.mvvm.di.module.ApplicationModule],
 *   keeping endpoint declarations clean and free of boilerplate auth parameters.
 */
interface NetworkService {

    /**
     * Fetches top headline news articles filtered by country, publisher sources, or language.
     *
     * Endpoint: `GET v2/top-headlines`
     */
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("sources") sources: String = "",
        @Query("language") language: String = "en"
    ): TopHeadlinesResponse

    /**
     * Fetches the list of available news publishers and sources.
     *
     * Endpoint: `GET v2/sources`
     */
    @GET("sources")
    suspend fun getNewsSources(): SourcesResponse

    /**
     * Searches for news articles matching a keyword query across the full article index.
     *
     * Endpoint: `GET v2/everything`
     */
    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1,
        @Query("sortBy") sortBy: String = "publishedAt"
    ): TopHeadlinesResponse
}

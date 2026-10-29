package com.sample.mvvm.data.repository

import com.sample.mvvm.data.api.NetworkService
import com.sample.mvvm.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository responsible for fetching filtered news article feeds.
 *
 * **Architecture Role:**
 * Connects the remote [NetworkService] API to [com.sample.mvvm.ui.newslist.NewsListViewModel].
 *
 * **Implementation Decisions:**
 * Wraps Retrofit `suspend` network queries inside a cold Kotlin [Flow] to emit article collections reactively.
 *
 * @property networkService Retrofit network API client injected via Hilt.
 */
@Singleton
class NewsRepository @Inject constructor(
    private val networkService: NetworkService
) {
    /**
     * Requests top headlines matching the given publisher source, country, or language filters.
     *
     * @param source Optional publisher ID string (e.g. "bbc-news").
     * @param country Optional ISO country code string (e.g. "us").
     * @param language Optional ISO language code string (e.g. "en").
     * @return Cold Flow emitting the list of matching [Article] items.
     */
    suspend fun getNews(
        source: String? = null,
        country: String? = null,
        language: String? = null
    ): Flow<List<Article>> = flow {
        try {
            val response = networkService.getTopHeadlines(
                country = country ?: "",
                sources = source ?: "",
                language = language ?: ""
            )
            emit(response.articles)
        } catch (e: Exception) {
            throw e
        }
    }
}

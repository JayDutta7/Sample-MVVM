package com.sample.mvvm.data.repository

import com.sample.mvvm.data.api.NetworkService
import com.sample.mvvm.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository responsible for retrieving top headlines.
 *
 * **Architecture Role:**
 * Supplies headline streams to [com.sample.mvvm.ui.topheadlines.TopHeadlineViewModel].
 *
 * @property networkService Injected Retrofit API client.
 */
@Singleton
class TopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService
) {
    /**
     * Fetches top headline news articles for a specified country code.
     *
     * @param country ISO country code (e.g., "us").
     * @return Flow emitting list of top headline [Article] items.
     */
    fun getTopHeadlines(country: String): Flow<List<Article>> = flow {
        val articles = networkService.getTopHeadlines(country).articles
        emit(articles)
    }
}

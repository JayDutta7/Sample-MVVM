package com.sample.mvvm.data.repository

import com.sample.mvvm.data.api.NetworkService
import com.sample.mvvm.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository responsible for performing article search operations.
 *
 * **Architecture Role:**
 * Connects the remote `/v2/everything` endpoint to [com.sample.mvvm.ui.search.SearchViewModel].
 *
 * @property networkService Injected Retrofit REST client.
 */
@Singleton
class SearchRepository @Inject constructor(
    private val networkService: NetworkService
) {
    /**
     * Performs a keyword search across news articles.
     *
     * @param query The search term inputted by the user.
     * @param pageSize Max items to return per page.
     * @param page Page index for pagination.
     * @param sortBy Sort order string (e.g. "publishedAt").
     * @return Flow emitting list of matching [Article] items.
     */
    fun searchNews(
        query: String,
        pageSize: Int = 20,
        page: Int = 1,
        sortBy: String = "publishedAt"
    ): Flow<List<Article>> = flow {
        val response = networkService.searchNews(
            query = query,
            pageSize = pageSize,
            page = page,
            sortBy = sortBy
        )
        emit(response.articles ?: emptyList())
    }
}

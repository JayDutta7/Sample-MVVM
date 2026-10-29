package com.sample.mvvm.data.repository

import com.sample.mvvm.data.api.NetworkService
import com.sample.mvvm.data.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository responsible for fetching available news publisher sources.
 *
 * **Architecture Role:**
 * Supplies publisher source feeds to [com.sample.mvvm.ui.news_sources.NewsSourcesViewModel].
 *
 * @property networkService Injected Retrofit API interface.
 */
@Singleton
class NewsSourcesRepository @Inject constructor(
    private val networkService: NetworkService
) {
    /**
     * Fetches the list of available publisher sources.
     *
     * @return Flow emitting list of [Source] items, or throwing an exception if the API status is not "ok".
     */
    fun getNewsSources(): Flow<List<Source>> = flow {
        val response = networkService.getNewsSources()
        if (response.status == "ok") {
            emit(response.sources ?: emptyList())
        } else {
            throw Exception(response.message ?: "Unknown error")
        }
    }
}

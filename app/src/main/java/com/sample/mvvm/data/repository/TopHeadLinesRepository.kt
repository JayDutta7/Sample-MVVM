package com.sample.mvvm.data.repository

import com.sample.mvvm.data.api.NetworkService
import com.sample.mvvm.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService,
) {
    fun getTopHeadlines(country: String): Flow<List<Article>> = flow {
        // Perform API call and map the response to a list of articles
        val articles = networkService.getTopHeadlines(country).articles
        // Emit the list of articles as a Flow
        emit(articles)
    }
}

package com.sample.mvvm.data.repository

import com.sample.mvvm.data.api.NetworkService
import com.sample.mvvm.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val networkService: NetworkService
) {
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

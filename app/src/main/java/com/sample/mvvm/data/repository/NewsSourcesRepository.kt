package com.sample.mvvm.data.repository

import com.sample.mvvm.data.api.NetworkService
import com.sample.mvvm.data.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsSourcesRepository @Inject constructor(
    private val networkService: NetworkService
) {
    fun getNewsSources(): Flow<List<Source>> = flow {
        val response = networkService.getNewsSources()
        if (response.status == "ok") {
            emit(response.sources ?: emptyList())
        } else {
            throw Exception(response.message ?: "Unknown error")
        }
    }
}

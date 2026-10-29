package com.sample.mvvm.ui.newslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.mvvm.data.model.Article
import com.sample.mvvm.data.repository.NewsRepository
import com.sample.mvvm.di.BackgroundContext
import com.sample.mvvm.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel managing UI state for [NewsListScreen].
 *
 * **Architecture Role:**
 * Handles news article filtering by publisher source, country, or language.
 *
 * **Implementation Decisions:**
 * Supports dual-language filtering by fetching two language streams concurrently using [zip],
 * combining results, deduplicating articles by URL, and ordering by publication timestamp.
 */
@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: NewsRepository,
    @BackgroundContext private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Initial)
    
    /** Observable state flow emitting matching article feed results. */
    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    private var currentSource: String? = null
    private var currentCountry: String? = null
    private var currentLanguage: String? = null

    /**
     * Loads news articles matching the specified publisher source, country, or language parameters.
     *
     * @param source Optional publisher source ID filter.
     * @param country Optional country code filter.
     * @param language Optional comma-separated language code filter.
     */
    fun loadNews(source: String? = null, country: String? = null, language: String? = null) {
        currentSource = source ?: currentSource
        currentCountry = country ?: currentCountry

        val languages = language?.split(",")?.map { it.trim() }?.filter { it.isNotBlank() }?.take(2)
            ?: emptyList()
        currentLanguage = languages.firstOrNull()

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val articles = if (languages.size >= 2) {
                // If 2 languages selected, fetch both feeds in parallel and combine
                val firstLangNews = repository.getNews(
                    source = currentSource,
                    country = currentCountry,
                    language = languages[0]
                )
                val secondLangNews = repository.getNews(
                    source = currentSource,
                    country = currentCountry,
                    language = languages[1]
                )
                // Combine and remove duplicate articles by canonical URL
                firstLangNews.zip(secondLangNews) { first, second ->
                    return@zip (first + second).distinctBy { it.url }
                        .sortedByDescending { it.publishedAt }
                }.flowOn(dispatcher)
            } else {
                // Single language or default query
                repository.getNews(
                    source = currentSource,
                    country = currentCountry,
                    language = currentLanguage
                ).flowOn(dispatcher)
            }.catch { e ->
                _uiState.value = UiState.Error(e.message ?: "An unknown error occurred")
            }
                .collect { articles ->
                    _uiState.value = UiState.Success(articles)
                }
        }
    }
}

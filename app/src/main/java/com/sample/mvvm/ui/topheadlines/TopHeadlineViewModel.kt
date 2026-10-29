package com.sample.mvvm.ui.topheadlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.mvvm.data.model.Article
import com.sample.mvvm.data.repository.TopHeadlineRepository
import com.sample.mvvm.di.BackgroundContext
import com.sample.mvvm.ui.base.UiState
import com.sample.mvvm.utils.AppConstant.COUNTRY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlineViewModel @Inject constructor(
    private val repository: TopHeadlineRepository,
    @BackgroundContext private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Initial)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    fun loadTopHeadlines(country: String = COUNTRY) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getTopHeadlines(country)
                .flowOn(dispatcher)
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "An unknown error occurred")
                }
                .collect { articles ->
                    _uiState.value = UiState.Success(articles)
                }
        }
    }
}

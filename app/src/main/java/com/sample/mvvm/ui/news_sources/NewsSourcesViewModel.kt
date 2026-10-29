package com.sample.mvvm.ui.news_sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.mvvm.data.model.Source
import com.sample.mvvm.data.repository.NewsSourcesRepository
import com.sample.mvvm.di.BackgroundContext
import com.sample.mvvm.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsSourcesViewModel @Inject constructor(
    private val repository: NewsSourcesRepository,
    @BackgroundContext private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _newsSources = MutableStateFlow<UiState<List<Source>>>(UiState.Initial)
    val newsSources: StateFlow<UiState<List<Source>>> = _newsSources

    fun loadNewsSources() {
        viewModelScope.launch {
            _newsSources.value = UiState.Loading
            repository.getNewsSources()
                .flowOn(dispatcher)
                .catch { e ->
                    _newsSources.value = UiState.Error(e.message ?: "Unknown error")
                }
                .collect { sources ->
                    _newsSources.value = UiState.Success(sources)
                }
        }
    }
}

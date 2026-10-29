package com.sample.mvvm.ui.languages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.mvvm.data.model.Language
import com.sample.mvvm.data.repository.LanguagesRepository
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

/**
 * ViewModel managing UI state for [LanguagesScreen].
 *
 * **Architecture Role:**
 * Fetches supported language options from [LanguagesRepository] and exposes them to the UI layer.
 */
@HiltViewModel
class LanguagesViewModel @Inject constructor(
    private val languagesRepository: LanguagesRepository,
    @BackgroundContext private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _languages = MutableStateFlow<UiState<List<Language>>>(UiState.Initial)
    
    /** Observable state flow holding available language filtering options. */
    val languages: StateFlow<UiState<List<Language>>> = _languages

    /**
     * Triggers asynchronous fetching of supported languages.
     */
    fun loadLanguages() {
        viewModelScope.launch {
            _languages.value = UiState.Loading
            languagesRepository.getLanguages()
                .flowOn(dispatcher)
                .catch { e ->
                    _languages.value = UiState.Error(e.message ?: "Unknown error occurred")
                }
                .collect { result ->
                    _languages.value = UiState.Success(result)
                }
        }
    }
}

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
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LanguagesViewModel @Inject constructor(
    val languagesRepository: LanguagesRepository,
    @BackgroundContext val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _languages = MutableStateFlow<UiState<List<Language>>>(UiState.Initial)
    val languages: StateFlow<UiState<List<Language>>> = _languages

    fun loadLanguages() {
        // Load languages from a data source (e.g., API, database) and update the _languages LiveData with the sorted list
        viewModelScope.launch {
            try {
                _languages.value = UiState.Loading
                languagesRepository.getLanguages().flowOn(dispatcher).collect { result ->
                    _languages.value = UiState.Success(result)
                }
            } catch (e: Exception) {
                _languages.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}

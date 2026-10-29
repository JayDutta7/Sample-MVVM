package com.sample.mvvm.ui.country_sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.mvvm.data.model.Country
import com.sample.mvvm.data.repository.CountriesRepository
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
 * ViewModel managing UI state for [CountrySourcesScreen].
 *
 * **Architecture Role:**
 * Requests country selection options from [CountriesRepository] and exposes `StateFlow<UiState<List<Country>>>`
 * to the Compose UI layer.
 *
 * **Implementation Decisions:**
 * - Injects [CountriesRepository] and I/O [CoroutineDispatcher] via Hilt `@HiltViewModel`.
 * - Sorts countries alphabetically by localized name before emitting [UiState.Success].
 */
@HiltViewModel
class CountrySourcesViewModel @Inject constructor(
    private val countriesRepository: CountriesRepository,
    @BackgroundContext private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _countries = MutableStateFlow<UiState<List<Country>>>(UiState.Initial)
    
    /** Observable state flow representing country list loading status and data. */
    val countries: StateFlow<UiState<List<Country>>> = _countries

    init {
        loadCountries()
    }

    /**
     * Loads and sorts the list of countries alphabetically by display name.
     */
    fun loadCountries() {
        viewModelScope.launch {
            _countries.value = UiState.Loading
            countriesRepository.getCountries()
                .flowOn(dispatcher)
                .catch { e ->
                    _countries.value = UiState.Error(e.message ?: "Unknown error occurred")
                }
                .collect { countriesList ->
                    val sortedList = countriesList.sortedBy { it.name }
                    _countries.value = UiState.Success(sortedList)
                }
        }
    }
}

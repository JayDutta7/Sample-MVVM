package com.sample.mvvm.ui.base

/**
 * Generic sealed class modeling asynchronous UI state transitions across the application.
 *
 * **Architecture Role:**
 * Serves as the standardized contract between ViewModels and Compose screens in the MVVM architecture.
 * ViewModels expose `StateFlow<UiState<T>>` which composables collect via `collectAsStateWithLifecycle()`.
 *
 * **Variants:**
 * - [Initial]: Uninitialized state prior to triggered data loading.
 * - [Loading]: Async operation in progress; UI shows loading indicator.
 * - [Success]: Async operation succeeded; contains payload [data].
 * - [Error]: Async operation failed; contains error [message].
 *
 * @param T Type of data payload carried in [Success].
 */
sealed class UiState<out T> {
    /** Represents successful data loading carrying the payload [data]. */
    data class Success<out T>(val data: T) : UiState<T>()
    
    /** Represents an error state carrying an optional [message]. */
    data class Error(val message: String? = null) : UiState<Nothing>()
    
    /** Represents an active loading state. */
    object Loading : UiState<Nothing>()
    
    /** Represents the default uninitialized initial state. */
    object Initial : UiState<Nothing>()
}

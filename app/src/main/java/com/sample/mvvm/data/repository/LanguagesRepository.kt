package com.sample.mvvm.data.repository

import com.sample.mvvm.data.model.Language
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository providing access to supported news filtering languages.
 *
 * **Architecture Role:**
 * Acts as the data abstraction providing language selection options to [com.sample.mvvm.ui.languages.LanguagesViewModel].
 *
 * **Implementation Decisions:**
 * Managed as a Hilt `@Singleton` exposing a cold [Flow] to provide a consistent reactive interface.
 */
@Singleton
class LanguagesRepository @Inject constructor() {

    /** Emits the supported [Language] options as a Flow stream. */
    fun getLanguages(): Flow<List<Language>> = flow {
        emit(languagesList)
    }

    companion object {
        /** Predefined list of supported filtering languages. */
        val languagesList = listOf(
            Language("en", "English", "English", "🇬🇧"),
            Language("es", "Spanish", "Español", "🇪🇸"),
            Language("fr", "French", "Français", "🇫🇷"),
            Language("de", "German", "Deutsch", "🇩🇪"),
            Language("hi", "Hindi", "हिंदी", "🇮🇳"),
            Language("ar", "Arabic", "العربية", "🇸🇦"),
            Language("zh", "Chinese", "中文", "🇨🇳"),
            Language("ja", "Japanese", "日本語", "🇯🇵"),
            Language("ru", "Russian", "Русский", "🇷🇺"),
            Language("pt", "Portuguese", "Português", "🇵🇹")
        )
    }
}

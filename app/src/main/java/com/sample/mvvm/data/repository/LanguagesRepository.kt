package com.sample.mvvm.data.repository

import com.sample.mvvm.data.model.Language
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for accessing language data.
 *
 * This class provides a list of supported languages for the application.
 * It is a Singleton, meaning only one instance of this class will exist.
 *
 * @property context The application context, injected by Hilt.
 */
@Singleton
class LanguagesRepository @Inject constructor() {
    fun getLanguages(): Flow<List<Language>> = flow {
        emit(languagesList)
    }

    companion object {
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
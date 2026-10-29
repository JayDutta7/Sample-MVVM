package com.sample.mvvm.data.model

/**
 * Model representing a supported news language option for filtering articles.
 *
 * **Architecture Role:**
 * Used by [com.sample.mvvm.data.repository.LanguagesRepository] and [com.sample.mvvm.ui.languages.LanguagesScreen]
 * to allow multi-language article selection and filtering.
 *
 * **Implementation Decisions:**
 * Includes both English and native language representations for localized user display.
 *
 * @property code Two-letter ISO 639-1 language code (e.g., "en", "es", "fr").
 * @property name English name of the language (e.g., "Spanish").
 * @property nativeName Endonym / native name of the language (e.g., "Español").
 * @property flag Flag emoji associated with the primary region using the language.
 */
data class Language(
    val code: String,
    val name: String,
    val nativeName: String = "",
    val flag: String = ""
)

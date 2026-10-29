package com.sample.mvvm.data.model

/**
 * Model representing a selectable country option for news filtering.
 *
 * **Architecture Role:**
 * Used by [com.sample.mvvm.data.repository.CountriesRepository] and [com.sample.mvvm.ui.country_sources.CountrySourcesScreen]
 * to allow users to filter top headlines by geographic region.
 *
 * **Implementation Decisions:**
 * Data class encapsulating country code, display name, and Unicode flag emoji for an intuitive UI display.
 *
 * @property code Two-letter ISO 3166-1 alpha-2 country code (e.g., "us", "in", "gb").
 * @property name Human-readable localized country name (e.g., "United States", "India").
 * @property flag Emoji representation of the national flag (e.g., "🇺🇸", "🇮🇳").
 */
data class Country(
    val code: String,
    val name: String,
    val flag: String
)

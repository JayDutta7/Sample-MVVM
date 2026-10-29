package com.sample.mvvm.data.repository

import com.sample.mvvm.data.model.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for accessing a static list of countries.
 * This class provides a Flow of Country objects, which can be observed for changes.
 * The list of countries is predefined and does not change.
 */
@Singleton
class CountriesRepository @Inject constructor() {
    fun getCountries(): Flow<List<Country>> = flow {
        emit(countriesList)
    }

    companion object {
        val countriesList = listOf(
            Country("ar", "Argentina", "🇦🇷"),
            Country("at", "Austria", "🇦🇹"),
            Country("au", "Australia", "🇦🇺"),
            Country("be", "Belgium", "🇧🇪"),
            Country("bg", "Bulgaria", "🇧🇬"),
            Country("br", "Brazil", "🇧🇷"),
            Country("ca", "Canada", "🇨🇦"),
            Country("cn", "China", "🇨🇳"),
            Country("co", "Colombia", "🇨🇴"),
            Country("cu", "Cuba", "🇨🇺"),
            Country("cz", "Czech Republic", "🇨🇿"),
            Country("de", "Germany", "🇩🇪"),
            Country("eg", "Egypt", "🇪🇬"),
            Country("fr", "France", "🇫🇷"),
            Country("gr", "Greece", "🇬🇷"),
            Country("hk", "Hong Kong", "🇭🇰"),
            Country("hu", "Hungary", "🇭🇺"),
            Country("id", "Indonesia", "🇮🇩"),
            Country("ie", "Ireland", "🇮🇪"),
            Country("in", "India", "🇮🇳"),
            Country("il", "Israel", "🇮🇱"),
            Country("it", "Italy", "🇮🇹"),
            Country("jp", "Japan", "🇯🇵"),
            Country("lt", "Lithuania", "🇱🇹"),
            Country("lv", "Latvia", "🇱🇻"),
            Country("ma", "Morocco", "🇲🇦"),
            Country("my", "Malaysia", "🇲🇾"),
            Country("mx", "Mexico", "🇲🇽"),
            Country("nl", "Netherlands", "🇳🇱"),
            Country("nz", "New Zealand", "🇳🇿"),
            Country("ng", "Nigeria", "🇳🇬"),
            Country("no", "Norway", "🇳🇴"),
            Country("ph", "Philippines", "🇵🇭"),
            Country("pl", "Poland", "🇵🇱"),
            Country("pt", "Portugal", "🇵🇹"),
            Country("ro", "Romania", "🇷🇴"),
            Country("rs", "Serbia", "🇷🇸"),
            Country("ru", "Russia", "🇷🇺"),
            Country("sa", "Saudi Arabia", "🇸🇦"),
            Country("sg", "Singapore", "🇸🇬"),
            Country("si", "Slovenia", "🇸🇮"),
            Country("sk", "Slovakia", "🇸🇰"),
            Country("za", "South Africa", "🇿🇦"),
            Country("kr", "South Korea", "🇰🇷"),
            Country("se", "Sweden", "🇸🇪"),
            Country("ch", "Switzerland", "🇨🇭"),
            Country("tw", "Taiwan", "🇹🇼"),
            Country("th", "Thailand", "🇹🇭"),
            Country("tr", "Turkey", "🇹🇷"),
            Country("ua", "Ukraine", "🇺🇦"),
            Country("ae", "United Arab Emirates", "🇦🇪"),
            Country("gb", "United Kingdom", "🇬🇧"),
            Country("us", "United States", "🇺🇸"),
            Country("ve", "Venezuela", "🇻🇪")
        )
    }
}
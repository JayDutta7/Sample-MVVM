package com.sample.mvvm.navigation

/**
 * Sealed class modeling navigation destination route definitions for Navigation Compose.
 *
 * **Architecture Role:**
 * Encapsulates route string constants used by [com.sample.mvvm.MainActivity]'s `NavHost` to decouple
 * navigation calls from raw string literals.
 *
 * @property route Unique string identifier route matching destination composables.
 */
sealed class Screen(val route: String) {
    /** Route for Main Dashboard Screen. */
    object Main : Screen("main")
    
    /** Route for Top Headlines Feed Screen. */
    object TopHeadlines : Screen("top_headlines")
    
    /** Route for Publisher News Sources Selection Screen. */
    object NewsSources : Screen("news_sources")
    
    /** Route for Filtered Articles List Screen. */
    object NewsList : Screen("news_list")
    
    /** Route for Country Filter Selection Screen. */
    object CountrySources : Screen("country_sources")
    
    /** Route for Language Filter Selection Screen. */
    object Languages : Screen("languages")
    
    /** Route for Article Keyword Search Screen. */
    object Search : Screen("search")
}

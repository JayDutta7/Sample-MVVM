package com.sample.mvvm.utils

import com.sample.mvvm.BuildConfig

/**
 * Centralized constant definitions object.
 *
 * **Architecture Role:**
 * Stores global immutable configuration defaults used across networking and ViewModel initialization.
 */
object AppConstant {

    /** API Key retrieved from local.properties / BuildConfig. */
    const val API_KEY = BuildConfig.API_KEY

    /** Default fallback country filter code ("us"). */
    const val COUNTRY = "us"
}

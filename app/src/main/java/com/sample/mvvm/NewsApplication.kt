package com.sample.mvvm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Primary [Application] class annotated with [@HiltAndroidApp][HiltAndroidApp].
 *
 * **Architecture Role:**
 * Triggers Hilt's code generation and initializes the application-level dependency injection graph.
 */
@HiltAndroidApp
class NewsApplication : Application()

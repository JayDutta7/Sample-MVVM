package com.sample.mvvm.di

import javax.inject.Qualifier

/**
 * Hilt custom Qualifier annotation for injecting the base URL string.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

/**
 * Hilt custom Qualifier annotation for injecting the I/O Coroutine Dispatcher ([kotlinx.coroutines.Dispatchers.IO]).
 *
 * Used across repositories and ViewModels to explicitly designate background execution thread pools.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BackgroundContext

/**
 * Hilt custom Qualifier annotation for injecting the Main Coroutine Dispatcher ([kotlinx.coroutines.Dispatchers.Main]).
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainContext

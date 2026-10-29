package com.sample.mvvm.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sample.mvvm.data.api.NetworkService
import com.sample.mvvm.di.BackgroundContext
import com.sample.mvvm.di.BaseUrl
import com.sample.mvvm.di.MainContext
import com.sample.mvvm.utils.AppConstant.API_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Primary Hilt Dependency Injection Module installed in [SingletonComponent].
 *
 * **Architecture Role:**
 * Provides application-wide singleton dependencies including HTTP networking clients ([OkHttpClient], [Retrofit]),
 * API service instances ([NetworkService]), JSON serialization ([Gson]), and Coroutine Dispatchers.
 *
 * **Implementation Decisions:**
 * - Declared as a Kotlin `object` to avoid class instantiation overhead during Hilt module setup.
 * - Automatically injects `apiKey` as a query parameter into every outgoing HTTP request via [provideApiKeyInterceptor].
 */
@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    /** Provides the Base URL for News API endpoints. */
    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://newsapi.org/v2/"

    /** Provides the application-wide [Gson] serializer/deserializer instance. */
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    /** Provides the Retrofit [GsonConverterFactory] using the configured Gson instance. */
    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    /**
     * Provides an OkHttp [Interceptor] that appends the API Key parameter automatically to every request.
     *
     * This avoids having to pass `apiKey` manually in every API interface call.
     */
    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val url = original.url.newBuilder()
            .addQueryParameter("apiKey", API_KEY)
            .build()
        chain.proceed(original.newBuilder().url(url).build())
    }

    /** Provides the configured [OkHttpClient] with timeouts and API key interceptor attached. */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        apiKeyInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /** Provides the configured [Retrofit] HTTP client builder. */
    @Provides
    @Singleton
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    /** Provides the Retrofit implementation of [NetworkService]. */
    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

    /** Provides the I/O Coroutine Dispatcher annotated with [@BackgroundContext][BackgroundContext]. */
    @BackgroundContext
    @Provides
    @Singleton
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    /** Provides the Main Coroutine Dispatcher annotated with [@MainContext][MainContext]. */
    @MainContext
    @Provides
    @Singleton
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

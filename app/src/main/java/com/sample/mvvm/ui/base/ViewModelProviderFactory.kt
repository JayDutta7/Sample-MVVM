package com.sample.mvvm.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Custom Factory class implementing [ViewModelProvider.Factory] for manually instantiating ViewModels.
 *
 * **Architecture Role:**
 * Retained for backward compatibility when instantiating ViewModels outside Hilt dependency injection.
 * Modern screens use `@HiltViewModel` and `hiltViewModel()` directly instead.
 *
 * @param T Specific [ViewModel] subtype being instantiated.
 * @property kClass Reflective [KClass] reference of the target ViewModel type.
 * @property creator Factory lambda invoking constructor instantiation.
 */
@Singleton
class ViewModelProviderFactory<T : ViewModel>(
    private val kClass: KClass<T>,
    private val creator: () -> T
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalArgumentException::class)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(kClass.java)) return creator() as T
        throw IllegalArgumentException("Unknown ViewModel class name: ${modelClass.name}")
    }
}

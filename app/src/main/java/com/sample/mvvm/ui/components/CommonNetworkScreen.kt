package com.sample.mvvm.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sample.mvvm.R
import com.sample.mvvm.ui.base.UiState

/**
 * Reusable, strongly-typed generic screen container handling asynchronous [UiState] transitions.
 *
 * **Architecture Role:**
 * Encapsulates Scaffold layout, top bar rendering, and declarative state switching ([UiState.Initial],
 * [UiState.Loading], [UiState.Success], and [UiState.Error]) across all primary feature screens.
 *
 * @param title Top app bar header text.
 * @param uiState Observable screen UI state of type [UiState<T>].
 * @param onBackPressed Optional back button click callback handler.
 * @param onRetry Optional callback invoked when the user taps "Retry" on error screens.
 * @param actions Composable action slot for top app bar icons.
 * @param onInitial Content composable rendered during [UiState.Initial].
 * @param onSuccess Content composable rendered during [UiState.Success], receiving strongly-typed payload data.
 */
@Composable
fun <T> CommonNetworkScreen(
    title: String,
    uiState: UiState<T>,
    onBackPressed: (() -> Unit)? = null,
    onRetry: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    onInitial: @Composable (modifier: Modifier) -> Unit = {},
    onSuccess: @Composable (data: T, modifier: Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            CommonTopBar(
                text = title,
                onBackClick = onBackPressed,
                actions = actions
            )
        }
    ) { padding ->
        val modifier = Modifier.padding(padding)
        when (uiState) {
            is UiState.Initial -> {
                onInitial(modifier)
            }

            is UiState.Success -> {
                onSuccess(uiState.data, modifier)
            }

            is UiState.Loading -> {
                LoadingIndicator(modifier = modifier)
            }

            is UiState.Error -> {
                ErrorAndRetryState(
                    message = uiState.message ?: stringResource(id = R.string.error_loading_content),
                    onRetry = onRetry,
                    modifier = modifier
                )
            }
        }
    }
}

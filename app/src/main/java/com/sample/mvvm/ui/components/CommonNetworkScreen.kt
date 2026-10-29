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
 * A composable function that creates a common network screen with various states handling.
 *
 * This function sets up a scaffold with a top bar and handles different UI states
 * (Initial, Success, Loading, Error) based on the provided [uiState].
 *
 * @param title The title to be displayed in the top bar.
 * @param uiState The current UI state of type [UiState<T>] that determines which composable to display.
 * @param onBackPressed A lambda function to be invoked when the back button is pressed.
 * @param onRetry An optional lambda function to be invoked when retry is requested in the error state.
 * @param actions A composable function that defines additional actions in the top bar.
 * @param onInitial A composable function to be invoked when the UI state is Initial.
 * @param onSuccess A composable function to be invoked when the UI state is Success, passing strongly-typed data.
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
                onInitial(modifier = modifier)
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

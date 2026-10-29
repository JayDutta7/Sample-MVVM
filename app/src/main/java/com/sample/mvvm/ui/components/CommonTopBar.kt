package com.sample.mvvm.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sample.mvvm.R

/**
 * Standardized Material 3 centered top app bar component.
 *
 * **Architecture Role:**
 * Reusable header toolbar providing consistent title typography, primary theme colors,
 * back button navigation icon, and custom action button slots.
 *
 * @param text Header title text string displayed in center alignment.
 * @param actions Composable action items appended to the right side of the toolbar.
 * @param onBackClick Optional back arrow click listener. Displays back button when non-null.
 * @param theme MaterialTheme instance providing theme color tokens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopBar(
    text: String,
    actions: @Composable RowScope.() -> Unit = {},
    onBackClick: (() -> Unit)? = null,
    theme: MaterialTheme = MaterialTheme
) {
    CenterAlignedTopAppBar(
        title = {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = theme.colorScheme.primary,
            titleContentColor = theme.colorScheme.onPrimary,
            actionIconContentColor = theme.colorScheme.onPrimary,
            navigationIconContentColor = theme.colorScheme.onPrimary
        ),
        navigationIcon = if (onBackClick != null) {
            {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.navigate_back_button_desc)
                    )
                }
            }
        } else {
            {}
        },
        actions = actions
    )
}

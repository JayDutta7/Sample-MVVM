package com.sample.mvvm.ui.country_sources

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.mvvm.R
import com.sample.mvvm.data.model.Country
import com.sample.mvvm.ui.base.UiState
import com.sample.mvvm.ui.components.CommonNetworkScreen
import com.sample.mvvm.ui.components.EmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountrySourcesScreen(
    onBackClick: () -> Unit,
    onCountryClick: (Country) -> Unit,
    viewModel: CountrySourcesViewModel = hiltViewModel()
) {
    val uiState by viewModel.countries.collectAsStateWithLifecycle()
    LaunchedEffect(uiState) {
        if (uiState is UiState.Initial) {
            viewModel.loadCountries()
        }
    }

    CommonNetworkScreen(
        title = stringResource(R.string.select_country),
        uiState = uiState,
        onBackPressed = onBackClick,
        onRetry = { viewModel.loadCountries() },
        onSuccess = { countries, modifier ->
            if (countries.isEmpty()) {
                EmptyState(
                    message = stringResource(R.string.no_countries_available),
                    modifier = modifier
                )
            } else {
                CountryList(
                    countries = countries,
                    onCountryClick = onCountryClick,
                    modifier = modifier
                )
            }
        }
    )
}

@Composable
private fun CountryList(
    countries: List<Country>,
    onCountryClick: (Country) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(countries) { country ->
            CountryItem(
                country = country,
                onClick = { onCountryClick(country) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CountryItem(
    country: Country,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = country.flag,
                fontSize = 24.sp,
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(
                text = country.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

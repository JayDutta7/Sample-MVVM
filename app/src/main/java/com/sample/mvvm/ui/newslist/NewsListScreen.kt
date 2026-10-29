package com.sample.mvvm.ui.newslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.sample.mvvm.R
import com.sample.mvvm.data.model.Article
import com.sample.mvvm.data.model.Source
import com.sample.mvvm.ui.base.UiState
import com.sample.mvvm.ui.components.CommonNetworkScreen
import com.sample.mvvm.ui.components.EmptyState
import com.sample.mvvm.ui.theme.NewsAppTheme
import com.sample.mvvm.utils.CustomTabsHelper
import com.sample.mvvm.utils.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    source: String? = null,
    country: String? = null,
    language: String? = null,
    onBackClick: () -> Unit,
    viewModel: NewsListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(uiState) {
        if (uiState is UiState.Initial) {
            viewModel.loadNews(source, country, language)
        }
    }
    CommonNetworkScreen(
        title = stringResource(id = R.string.news_sources),
        onBackPressed = onBackClick,
        uiState = uiState,
        onRetry = { viewModel.loadNews(source, country, language) },
        onSuccess = { articles, modifier ->
            if (articles.isNotEmpty()) {
                NewsList(
                    articles = articles,
                    onArticleClick = { article ->
                        CustomTabsHelper.launchUrl(context, article.url)
                    },
                    modifier = modifier
                )
            } else {
                EmptyState(
                    message = stringResource(id = R.string.no_sources_found),
                    modifier = modifier
                )
            }
        }
    )
}

@Composable
fun NewsList(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(articles, key = { it.url }) { article ->
            NewsArticleItem(
                article = article,
                onClick = { onArticleClick(article) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsArticleItem(
    article: Article,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            article.urlToImage?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(
                        model = imageUrl,
                        error = painterResource(id = R.drawable.ic_launcher_foreground)
                    ),
                    contentDescription = article.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = article.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.source.name ?: "",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                article.publishedAt.let { date ->
                    Text(
                        text = formatDate(date),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsArticleItemPreview() {
    NewsAppTheme {
        NewsArticleItem(
            article = Article(
                source = Source("source-id", "Source Name"),
                author = "Author Name",
                title = "Sample News Article Title That Can Be Quite Long and Might Wrap to Multiple Lines",
                description = "This is a sample news article description that provides a brief summary of the article content. It can be longer and might wrap to multiple lines.",
                url = "https://example.com",
                urlToImage = "https://picsum.photos/800/450",
                publishedAt = "2023-06-09T12:34:56Z",
                content = "Full content of the article would go here..."
            ),
            onClick = {}
        )
    }
}

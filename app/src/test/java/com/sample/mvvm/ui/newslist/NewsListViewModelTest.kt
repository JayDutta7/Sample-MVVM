package com.sample.mvvm.ui.newslist

import app.cash.turbine.test
import com.sample.mvvm.MainCoroutineRule
import com.sample.mvvm.data.api.FakeNetworkService
import com.sample.mvvm.data.model.Article
import com.sample.mvvm.data.model.Source
import com.sample.mvvm.data.repository.NewsRepository
import com.sample.mvvm.ui.base.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class NewsListViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val fakeNetworkService = FakeNetworkService()
    private val repository = NewsRepository(fakeNetworkService)
    private lateinit var viewModel: NewsListViewModel
    private val testDispatcher = coroutineRule.testDispatcher

    @Before
    fun setup() {
        viewModel = NewsListViewModel(repository, testDispatcher)
    }

    @Test
    fun loadNews_emitsLoadingAndSuccessStates() = runTest {
        val testArticles = listOf(
            Article(
                source = Source("1", "Source"),
                author = "Author",
                title = "Title 1",
                description = "Desc",
                url = "https://news1.com",
                urlToImage = null,
                publishedAt = "2023-01-01T00:00:00Z",
                content = null
            )
        )
        fakeNetworkService.setTopHeadlines(testArticles)

        viewModel.uiState.test {
            assertEquals(UiState.Initial, awaitItem())

            viewModel.loadNews(source = "bbc-news")

            val loadingItem = awaitItem()
            assertTrue(loadingItem is UiState.Loading)

            testDispatcher.scheduler.advanceUntilIdle()

            val successState = awaitItem() as UiState.Success
            assertEquals(testArticles, successState.data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun loadNews_whenError_emitsErrorState() = runTest {
        fakeNetworkService.setError(RuntimeException("Network failure"))

        viewModel.uiState.test {
            assertEquals(UiState.Initial, awaitItem())

            viewModel.loadNews(source = "bbc-news")

            val loadingItem = awaitItem()
            assertTrue(loadingItem is UiState.Loading)

            testDispatcher.scheduler.advanceUntilIdle()

            val errorState = awaitItem() as UiState.Error
            assertEquals("Network failure", errorState.message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}

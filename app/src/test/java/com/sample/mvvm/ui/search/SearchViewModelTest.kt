package com.sample.mvvm.ui.search

import app.cash.turbine.test
import com.sample.mvvm.MainCoroutineRule
import com.sample.mvvm.data.api.FakeNetworkService
import com.sample.mvvm.data.model.Article
import com.sample.mvvm.data.model.Source
import com.sample.mvvm.data.repository.SearchRepository
import com.sample.mvvm.ui.base.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class SearchViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val fakeNetworkService = FakeNetworkService()
    private val searchRepository = SearchRepository(fakeNetworkService)
    private lateinit var viewModel: SearchViewModel
    private val testDispatcher = coroutineRule.testDispatcher

    @Before
    fun setup() {
        viewModel = SearchViewModel(searchRepository, testDispatcher)
    }

    @Test
    fun setSearchQuery_updatesSearchQueryFlow() = runTest {
        viewModel.searchQuery.test {
            assertEquals("", awaitItem())

            viewModel.setSearchQuery("android")
            assertEquals("android", awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun searchNews_emitsSuccessState_whenArticlesFound() = runTest {
        val articles = listOf(
            Article(
                source = Source("1", "CNN"),
                author = "John",
                title = "Android Update",
                description = "Desc",
                url = "https://cnn.com/android",
                urlToImage = null,
                publishedAt = "2023-01-01T00:00:00Z",
                content = null
            )
        )
        fakeNetworkService.setTopHeadlines(articles)

        viewModel.uiState.test {
            assertEquals(UiState.Initial, awaitItem())

            viewModel.setSearchQuery("android")

            testDispatcher.scheduler.advanceTimeBy(300)
            testDispatcher.scheduler.advanceUntilIdle()

            var current = awaitItem()
            if (current is UiState.Loading) {
                current = awaitItem()
            }

            val successState = current as UiState.Success
            assertEquals(articles, successState.data)
            cancelAndIgnoreRemainingEvents()
        }
    }
}

package com.dev_marinov.kinopoiskapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev_marinov.kinopoiskapp.domain.model.movie.*
import com.dev_marinov.kinopoiskapp.domain.model.movie_combine.MovieItemCombine
import com.dev_marinov.kinopoiskapp.domain.model.selectable_favorite.SelectableFavoriteMovie
import com.dev_marinov.kinopoiskapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetFavoriteUseCaseTest {
    @Mock
    private lateinit var mockFavoriteRepository: FavoriteRepository
    private lateinit var getFavoriteUseCase: GetFavoriteUseCase
    private lateinit var selectableFavoriteMovie: SelectableFavoriteMovie


    // `InstantTaskExecutorRule` - это правило, которое заменяет исполнителя задач `LiveData`
    // на синхронный экзекутор, позволяя тестам запускаться на основном потоке
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        getFavoriteUseCase = GetFavoriteUseCase(favoriteRepository = mockFavoriteRepository)
        selectableFavoriteMovie = SelectableFavoriteMovie(
            movie = MovieItemCombine(
                movie = Movie(
                    id = 0,
                    type = "type",
                    name = "name",
                    description = "description",
                    movieLength = 0,
                    year = 0,
                    page = 0
                ),
                releaseYears = listOf(
                    ReleaseYear(
                        id = 0,
                        end = 0,
                        start = 0,
                        movieId = 0
                    )
                ),
                poster = Poster(
                    id = 0,
                    previewUrl = "previewUrl",
                    url = "url",
                    movieId = 0
                ),
                rating = Rating(
                    id = 0,
                    kp = 0.0,
                    imdb = 0.0,
                    filmCritics = 0.0,
                    russianFilmCritics = 0.0,
                    await = 0.0,
                    movieId = 0
                ),
                votes = Votes(
                    id = 0,
                    kp = 0,
                    filmCritics = 0,
                    imdb = 0,
                    russianFilmCritics = 0,
                    await = 0,
                    movieId = 0
                ),
                genres = listOf(
                    Genres(
                        id = 0,
                        genres = "genres",
                        movieId = 0
                    )
                ),
                persons = listOf(
                    Person(
                        id = 0,
                        photo = "photo",
                        name = "name",
                        movieId = 0
                    )
                ),
                videos = Videos(
                    trailers = listOf(
                        Trailer(
                            url = "url",
                            name = "name",
                            site = "site",
                            movieId = 0
                        )
                    ),
                    movieId = 0
                )
            ),
            isFavorite = true
        )

    }

    @After
    fun after() {
        Mockito.reset(mockFavoriteRepository)
    }

    @Test
    fun `execute return selectableFavoriteMovie list flow from repository`() = runTest {
        val expectedListSelectableFavoriteMovieFlow = listOf(selectableFavoriteMovie)

        `when`(mockFavoriteRepository.favoriteMoviesForHome).thenReturn(flowOf(expectedListSelectableFavoriteMovieFlow))

        val actualResult = getFavoriteUseCase.getFavoriteMoviesForHomeFlow().first()
        assertEquals(expectedListSelectableFavoriteMovieFlow, actualResult)
    }

    @Test
    fun `execute return selectableFavoriteMovie liveData list from repository`()  {

        val expectedListSelectableFavoriteMovie = listOf(selectableFavoriteMovie)

        val expectedLiveData = MutableLiveData<List<SelectableFavoriteMovie>>()
        expectedLiveData.value = expectedListSelectableFavoriteMovie

        `when`(mockFavoriteRepository.favoriteMoviesForDetail).thenReturn(expectedLiveData)
        val actualResult: LiveData<List<SelectableFavoriteMovie>> = getFavoriteUseCase.getFavoriteMoviesForDetailFlow()

        assertEquals(expectedLiveData, actualResult)
    }

    @Test
    fun `execute return countFavorite flow from repository`() = runTest {
        val expectedIntFlow = flowOf(0)
        `when`(mockFavoriteRepository.countFavorite).thenReturn(expectedIntFlow)
        val actualResult = getFavoriteUseCase.getCountFavoriteFlow()
        assertEquals(expectedIntFlow, actualResult)
    }

    @Test
    fun `execute deleteFavoriteMovie from repository`() = runTest {
        val parameters = GetFavoriteUseCase.GetFavoriteMovieParams(selectableFavoriteMovie)
        // Act
        getFavoriteUseCase.executeDelete(parameters)
        // Assert
        verify(mockFavoriteRepository).deleteFavoriteMovie(selectableFavoriteMovie)
    }

    @Test
    fun `execute saveFavoriteMovie from repository`() = runTest {
        // Задаем входные параметры для теста
        val parameters = GetFavoriteUseCase.GetFavoriteMovieParams(selectableFavoriteMovie)
        // Вызываем метод executeSave
        getFavoriteUseCase.executeSave(parameters)
        // Проверяем, что метод saveFavoriteMovie был вызван с заданными параметрами
        verify(mockFavoriteRepository).saveFavoriteMovie(selectableFavoriteMovie)
    }

    @Test
    fun `execute clearAllMovies`() = runTest {
        // В данном случае, метод `clearAllMovies()` не принимает аргументов и не возвращает ничего.
        // Поэтому в тесте фокусируемся только на проверке вызова этого метода на объекте `mockFavoriteRepository`.
        getFavoriteUseCase.executeClear()
        verify(mockFavoriteRepository).clearAllMovies()
    }

}
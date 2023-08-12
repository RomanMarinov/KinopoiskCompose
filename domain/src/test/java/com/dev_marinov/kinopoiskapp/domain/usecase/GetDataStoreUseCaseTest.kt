package com.dev_marinov.kinopoiskapp.domain.usecase

import android.provider.SyncStateContract.Constants
import androidx.compose.ui.graphics.Color
import com.dev_marinov.kinopoiskapp.domain.model.pagination.PagingParams
import com.dev_marinov.kinopoiskapp.domain.repository.DataStoreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import org.mockito.quality.Strictness

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetDataStoreUseCaseTest {

    @Mock
    private lateinit var mockDataStoreRepository: DataStoreRepository
    private lateinit var getDataStoreUseCase: GetDataStoreUseCase

    @Before
    fun setup() {
        getDataStoreUseCase = GetDataStoreUseCase(dataStoreRepository = mockDataStoreRepository)
    }

    @After
    fun after() {
        Mockito.reset(mockDataStoreRepository)
    }

    @Test
    fun `execute return boolean flow for getHideBottomBar from repository`() = runTest {
// Given
        val expectedValue = true
        `when`(mockDataStoreRepository.getHideBottomBar).thenReturn(flowOf(expectedValue))
        // When
        val resultFlow = mockDataStoreRepository.getHideBottomBar
        // Then
        resultFlow.collect { actualResult ->
            assertEquals(expectedValue, actualResult)
        }
    }

    @Test
    fun `execute saveScroll with params from repository`() = runTest {
        // Устанавливаем ожидаемый результат и поведение репозитория при вызове saveScroll
        val key = "key"
        val isHide = true
        val expectedResult = Unit // В данном случае saveScroll не возвращает результат

        `when`(mockDataStoreRepository.saveScroll(key, isHide)).thenReturn(expectedResult)

        // Вызываем метод, который мы хотим протестировать
        getDataStoreUseCase.saveScroll(key, isHide)

        // Проверяем, был ли метод saveScroll вызван на репозитории с ожидаемыми параметрами
        verify(mockDataStoreRepository).saveScroll(key, isHide)
    }


    @Test
    fun `execute return boolean flow for property getClickedFilter from repository`() = runTest {
        val expectedValue = true
        `when`(mockDataStoreRepository.getClickedFilter).thenReturn(flowOf(expectedValue))

        val actualResult = mockDataStoreRepository.getClickedFilter.first()

        assertEquals(expectedValue, actualResult)
    }

    @Test
    fun `execute saveClickedFilter with params from repository`() = runTest {
        // Устанавливаем ожидаемый результат и поведение репозитория при вызове saveScroll
        val key = "key"
        val isHide = true
        val expectedResult = Unit // В данном случае saveScroll не возвращает результат

        `when`(mockDataStoreRepository.saveClickedFilter(key, isHide)).thenReturn(expectedResult)

        // Вызываем метод, который мы хотим протестировать
        getDataStoreUseCase.saveClickedFilter(key, isHide)

        // Проверяем, был ли метод saveScroll вызван на репозитории с ожидаемыми параметрами
        verify(mockDataStoreRepository).saveClickedFilter(key, isHide)
    }

    @Test
    fun `execute return boolean flow for property getClickedTypeGenre from repository`() = runTest {
        val expectedValue = "genre"
        `when`(mockDataStoreRepository.getClickedTypeGenre).thenReturn(flowOf(expectedValue))

        val actualResult = mockDataStoreRepository.getClickedTypeGenre.first()

        assertEquals(expectedValue, actualResult)
    }

    @Test
    fun `execute saveGenreType with params from repository`() = runTest {
        // Устанавливаем ожидаемый результат и поведение репозитория при вызове saveScroll
        val key = "key"
        val typeGenre = "genre"
        val expectedResult = Unit // В данном случае saveScroll не возвращает результат

        `when`(mockDataStoreRepository.saveGenreType(key, typeGenre)).thenReturn(expectedResult)

        // Вызываем метод, который мы хотим протестировать
        getDataStoreUseCase.saveGenreType(key, typeGenre)

        // Проверяем, был ли метод saveScroll вызван на репозитории с ожидаемыми параметрами
        verify(mockDataStoreRepository).saveGenreType(key, typeGenre)
    }

    @Test
    fun `execute return boolean list list flow for property gradientColorsApp from repository`() =
        runTest {
            val expectedValue = flowOf(
                listOf(
                    listOf(
                        Color.Black, Color.Gray
                    )
                )
            )
            `when`(mockDataStoreRepository.gradientColorsApp).thenReturn(expectedValue)

            val actualResult = mockDataStoreRepository.gradientColorsApp

            assertEquals(expectedValue, actualResult)
        }

    @Test
    fun `execute return boolean list flow for property getGradientColorApp from repository`() =
        runTest {
            val expectedValue = flowOf(
                listOf(
                    Color.Black, Color.Gray
                )
            )
            `when`(mockDataStoreRepository.getGradientColorApp).thenReturn(expectedValue)

            val actualResult = mockDataStoreRepository.getGradientColorApp

            assertEquals(expectedValue, actualResult)
        }

//    suspend fun setGradientColorApp(selectedBoxIndex: Int)

    @Test
    fun `execute setGradientColorApp with param from repository`() = runTest {
        val selectedBoxIndex = 0
        val expectedIndex = Unit

        `when`(mockDataStoreRepository.setGradientColorApp(selectedBoxIndex)).thenReturn(
            expectedIndex
        )

        getDataStoreUseCase.setGradientColorApp(selectedBoxIndex)

        verify(mockDataStoreRepository).setGradientColorApp(selectedBoxIndex)
    }

    @Test
    fun `execute return int flow for property getGradientColorIndexApp from repository`() =
        runTest {
            val expectedValue = flowOf(0)
            `when`(mockDataStoreRepository.getGradientColorIndexApp).thenReturn(expectedValue)

            val actualResult = mockDataStoreRepository.getGradientColorIndexApp

            assertEquals(expectedValue, actualResult)
        }

    //   suspend fun saveGradientColorIndexApp(key: String, selectedBoxIndex: Int)
    @Test
    fun `execute saveGradientColorIndexApp with param from repository`() = runTest {
        val key = "key"
        val selectedBoxIndex = 0
        val expected = Unit

        `when`(mockDataStoreRepository.saveGradientColorIndexApp(key, selectedBoxIndex)).thenReturn(
            expected
        )

        getDataStoreUseCase.saveGradientColorIndexApp(key, selectedBoxIndex)

        verify(mockDataStoreRepository).saveGradientColorIndexApp(key, selectedBoxIndex)
    }

    @Test
    fun `execute savePagingParams with params from repository`() = runTest {
        val key = "key"
        val pagingParams = PagingParams(
            yearPickerTo = "0",
            yearPickerFrom = "0",
            ratingPickerTo = "0",
            ratingPickerFrom = "0",
            genre = "genre",
            page = 0,
            indexLoad = 0
        )
        val expected = Unit

        `when`(mockDataStoreRepository.savePagingParams(key, pagingParams)).thenReturn(expected)
        getDataStoreUseCase.savePagingParams(key, pagingParams)
        verify(mockDataStoreRepository).savePagingParams(key, pagingParams)
    }

    // //        suspend fun getPagingParams(key: String): PagingParams?

    @Test
    fun `execute return PagingParams for getPagingParams with params from repository`() = runTest {
        val key = "key"
        val pagingParams = PagingParams(
            yearPickerTo = "0",
            yearPickerFrom = "0",
            ratingPickerTo = "0",
            ratingPickerFrom = "0",
            genre = "genre",
            page = 0,
            indexLoad = 0
        )
        `when`(mockDataStoreRepository.getPagingParams(key)).thenReturn(pagingParams)

        val actualResult = mockDataStoreRepository.getPagingParams(key)
        assertEquals(pagingParams, actualResult)
    }
}
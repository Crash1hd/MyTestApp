package com.mycomp.mytestapp.utilities.log.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mycomp.mytestapp.utilities.log.db.LogRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.isA
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LogViewModelFactoryTest {
    /***
     * https://proandroiddev.com/how-to-unit-test-livedata-and-lifecycle-components-8a0af41c90d9
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockLogRepository: LogRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

    }

    @Test
    fun `does The LogViewModelFactory Class Return A LogViewModeFactory`() {
        assertThat(LogViewModelFactory(mockLogRepository), isA(LogViewModelFactory::class.java))
    }

    @Test
    fun `does the Create Function Return a LogViewModel`() {
        assertThat("test", LogViewModelFactory(mockLogRepository).create(LogViewModel::class.java), isA(
            LogViewModel::class.java))
    }

}
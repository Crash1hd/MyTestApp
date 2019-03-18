package com.mycomp.mytestapp.utilities.log

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mycomp.mytestapp.utilities.log.db.LogModel
import com.mycomp.mytestapp.utilities.log.db.LogRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.isA
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
class LogTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testLog1 = LogModel(0, "i", "Test", "Test1", "", 1, 0, 0.0, 0.0)

    @Mock
    lateinit var logRepo: LogRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        Log.setLogRepository(logRepo)
    }

    @Test
    fun `aaa`() {
        //`when`(logRepo.upsert(testLog1)).thenReturn(listOf(1))
        //assertThat("", Log.logRepo.hashCode(), `is`(logRepo.hashCode()))
        Log.i("Test", "Test1")
        verify(logRepo).upsert(testLog1)
    }

    @Test
    fun `on Calling function i it returns an Int`() {
        assertThat("Returned class is not an Int", Log.i("Test", "Test"), isA(Int::class.java))
        assertThat("Returned Int is not 0", Log.i("Test", "Test"), `is`(0))
    }

    @Test
    fun `on Calling function e it returns an Int`() {
        assertThat("Returned class is not an Int", Log.e("Test", "Test"), isA(Int::class.java))
        assertThat("Returned Int is not 0", Log.e("Test", "Test"), `is`(0))
    }

    @Test
    fun `on Calling function d it returns an Int`() {
        assertThat("Returned class is not an Int", Log.d("Test", "Test"), isA(Int::class.java))
        assertThat("Returned Int is not 0", Log.d("Test", "Test"), `is`(0))
    }

    @Test
    fun `on Calling function v it returns an Int`() {
        assertThat("Returned class is not an Int", Log.v("Test", "Test"), isA(Int::class.java))
        assertThat("Returned Int is not 0", Log.v("Test", "Test"), `is`(0))
    }

    @Test
    fun `on Calling function w it returns an Int`() {
        assertThat("Returned class is not an Int", Log.w("Test", "Test"), isA(Int::class.java))
        assertThat("Returned Int is not 0", Log.w("Test", "Test"), `is`(0))
    }

    @Test
    fun `on Deletion Of 2 Logs Older Than 7 Days Returns Count Of 2`() {
        val xDays = 7

        Log.purgeOldLogsGreaterThan(xDays)

        runBlocking {
            verify(logRepo, Mockito.times(1)).deleteAllLogsOlderThan(xDays)
        }
    }
}
package com.mycomp.mytestapp.utilities.log.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mycomp.mytestapp.data.LiveDataTestUtil
import com.mycomp.mytestapp.utilities.log.db.LogModel
import com.mycomp.mytestapp.utilities.log.db.LogRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class LogViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var logViewModel: LogViewModel

    private val testLog1 = LogModel(1, "d", "Test", "Test1", "", 1, 0, 0.0, 0.0)
    private val testLog2 = LogModel(2, "d", "Test", "Test2", "", 1, 0, 0.0, 0.0)

    private val tempLogModelList: List<LogModel> = listOf(testLog1, testLog2)
    private val tempLiveData = MutableLiveData<List<LogModel>>(tempLogModelList)

    //only used for failure checking
    private val testLog3 = LogModel(3, "d", "Test", "Test1", "", 1, 0, 0.0, 0.0)
    private val testLog4 = LogModel(4, "d", "Test", "Test2", "", 1, 0, 0.0, 0.0)

    private val tempLogModelListWrong: List<LogModel> = listOf(testLog3, testLog4)
    private val tempLiveDataWrong = MutableLiveData<List<LogModel>>(tempLogModelListWrong)

    @Mock
    private lateinit var logRepository: LogRepository

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(logRepository.getAllLogs()).thenReturn(tempLogModelList)
        `when`(logRepository.getAllLiveLogs()).thenReturn(tempLiveData)
        `when`(logRepository.upsert(testLog1)).thenReturn(listOf(1))
        `when`(logRepository.upsertAll(listOf(testLog1, testLog2))).thenReturn(listOf(2))
        `when`(logRepository.deleteAll()).thenReturn(tempLogModelList.count())

        logViewModel = LogViewModel(logRepository)
    }

    @Test
    fun `on Calling Getting All Logs On Empty Table Returns Empty Log Set`() {
        runBlocking {
            `when`(logRepository.getAllLogs()).thenReturn(listOf())
            assertThat("The Correct Logs have not been returned", logViewModel.getAllLogs.await(), `is`(listOf()))
        }
    }

    @Test
    fun `on Getting All Logs Should Return Correct Logs`() {
        runBlocking {
            assertThat("The Correct Logs have not been returned", logViewModel.getAllLogs.await(), `is`(tempLogModelList))
        }
    }

    @Test
    fun `on Calling Getting All Live Logs On Empty Table Returns An Empty Log Set`() {
        runBlocking {
            `when`(logRepository.getAllLiveLogs()).thenReturn(MutableLiveData<List<LogModel>>(listOf()))
            assertThat("The Correct Logs have not been returned", logViewModel.getAllLiveLogs.await().value, `is`(listOf()))
        }
    }

    @Test
    fun `on Getting All Live Logs Should Return The Correct Live Logs`() {
        //leaving in both test as they are different ways of getting the data from LiveLogs
        runBlocking {
            assertThat("The Correct Logs have not been returned", logViewModel.getAllLiveLogs.await().value, `is`(tempLogModelList))
            assertThat("The Correct Logs have not been returned", LiveDataTestUtil.getValue(logViewModel.getAllLiveLogs.await()), `is`(tempLogModelList))
        }
    }

    @Test
    fun `on Inserting A Single Log The Row Count Should Return 1 Row`() {
        assertThat("The Row count returned is incorrect. It Should be 1", logViewModel.upsert(testLog1), `is`(listOf(1.toLong())))
    }

}
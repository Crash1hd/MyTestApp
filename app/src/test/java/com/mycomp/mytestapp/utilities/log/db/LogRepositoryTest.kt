package com.mycomp.mytestapp.utilities.log.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mycomp.mytestapp.data.LiveDataTestUtil
import com.mycomp.mytestapp.utilities.log.Log
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.isA
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class LogRepositoryTest {

    /***
     * https://proandroiddev.com/how-to-unit-test-livedata-and-lifecycle-components-8a0af41c90d9
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var logRepository: LogRepository

    private val testLog1 = LogModel(1, "d", "Test", "Test1", "", 1, 0, 0.0, 0.0)
    private val testLog2 = LogModel(2, "d", "Test", "Test2", "", 1, 0, 0.0, 0.0)
    //private val testLog3 = LogModel(3,"d", "Test", "Test1","", 1, 0, 0.0, 0.0)
    //private val testLog4 = LogModel(4,"d", "Test", "Test2","", 1, 0, 0.0, 0.0)

    private val tempLogModelList: List<LogModel> = listOf(testLog1, testLog2)
    //private val tempLogModelListWrong: List<LogModel> = listOf(testLog3, testLog4)//only used for failure checking
    private val tempLiveData = MutableLiveData<List<LogModel>>(tempLogModelList)

    @Mock
    private lateinit var logDao: LogDao

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        //alternate way to create class mock (keeping for prosperity)
        //logDao = Mockito.mock(LogDao::class.java)

        `when`(logDao.getAllLogs()).thenReturn(tempLogModelList)
        `when`(logDao.getAllLiveLogs()).thenReturn(tempLiveData)
        `when`(logDao.upsert(testLog1)).thenReturn(listOf(1))
        `when`(logDao.upsertAll(listOf(testLog1, testLog2))).thenReturn(listOf(2))
        `when`(logDao.deleteAll()).thenReturn(tempLogModelList.count())

        logRepository = LogRepositoryImpl(logDao)
    }

    @Test
    fun `on Calling Getting All Logs On Empty Table Returns Empty Log Set`() {
        `when`(logDao.getAllLogs()).thenReturn(listOf())
        assertThat("The Correct Logs have not been returned", logRepository.getAllLogs(), `is`(listOf()))
    }

    @Test
    fun `on Getting All Logs Should Return Correct Logs`() {
        assertThat("The Correct Logs have not been returned", logRepository.getAllLogs(), `is`(tempLogModelList))
    }

    @Test
    fun `on Calling Getting All Live Logs On Empty Table Returns An Empty Log Set`() {
        `when`(logDao.getAllLiveLogs()).thenReturn(MutableLiveData<List<LogModel>>(listOf()))
        assertThat("The Correct Logs have not been returned", logRepository.getAllLiveLogs().value, `is`(listOf()))
    }

    @Test
    fun `on Getting All Live Logs Should Return The Correct Live Logs`() {
        //leaving in both test as they are different ways of getting the data from LiveLogs
        assertThat("The Correct Logs have not been returned", logRepository.getAllLiveLogs().value, `is`(tempLogModelList))
        assertThat("The Correct Logs have not been returned", LiveDataTestUtil.getValue(logRepository.getAllLiveLogs()), `is`(tempLogModelList))
    }

    @Test
    fun `on Inserting A Single Log The Row Count Should Return 1 Row`() {
        assertThat("The Row count returned is incorrect. It Should be 1", logRepository.upsert(testLog1), `is`(listOf(1.toLong())))
    }

    @Test
    fun `on Inserting 2 Logs The Row Count Should Return 2 Rows`() {
        assertThat("The Row count returned is incorrect. It Should be 2", logRepository.upsertAll(listOf(testLog1, testLog2)),  `is`(listOf(2.toLong())))
    }

    @Test
    fun `get Instance Method Should Return An Instance Of LogRepository`() {
        val logRepository = LogRepositoryImpl.getInstance(logDao)
        assertThat("The Instance returned is not that of LogRepository", logRepository, isA(LogRepository::class.java))
    }

    @Test
    fun `on Delete All Logs The Return Count Should Be 2`() {
        assertThat("Delete All Logs function did not return the count of 2", logRepository.deleteAll(), `is`(2))
    }

    @Test
    fun `on Deletion Of 2 Logs Older Than 7 Days Returns Count Of 2`() {
        val xDays = 7

        Log.setLogRepository(logRepository)

        logRepository.deleteAllLogsOlderThan(xDays)

        runBlocking {
            //verify(logDao, times(1)).deleteAllLogsOlderThan(xDays)
        }
    }
}
package com.mycomp.mytestapp.utilities.log.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mycomp.mytestapp.data.LiveDataTestUtil
import com.mycomp.mytestapp.data.MYTESTAPPRoomDatabase
import com.mycomp.mytestapp.data.observeOnce
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Database Testing Examples
 * https://developer.android.com/training/data-storage/room/testing-db
 * https://proandroiddev.com/testing-the-un-testable-and-beyond-with-android-architecture-components-part-1-testing-room-4d97dec0f451
 */
//TODO:  Move these tests to instrumentation test (androidTest) once I learn how to have proper code coverage
@RunWith(AndroidJUnit4::class)
class LoggerDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var testDatabase: MYTESTAPPRoomDatabase
    private lateinit var logDao: LogDao

    //Set all logID's to 0 for auto incrementing (makes resetIndex test more hermetic)
    private val testLog1 = LogModel(0, "d", "Test", "Test1", "", 1, 0, 0.0, 0.0)
    private val testLog2 = LogModel(0, "d", "Test", "Test2", "", 1, 0, 0.0, 0.0)
    private val testLog3 = LogModel(0, "d", "Test", "Test3", "", 1, 0, 0.0, 0.0)
    private val testLog4 = LogModel(0, "d", "Test", "Test4", "", 1, 0, 0.0, 0.0)
    private val testLog5 = LogModel(0, "d", "Test", "Test5", "", 1, 0, 0.0, 0.0)

    @Before
    @Throws(Exception::class)
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        //testDatabase = Room.inMemoryDatabaseBuilder(context, MYTESTAPPRoomDatabase::class.java).build()
        testDatabase = MYTESTAPPRoomDatabase.getMemoryInstance(context)//getDatabaseInstance(context, true)
        logDao = testDatabase.logDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        testDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun writePostAndReadInList() {
        runBlocking {
            withContext(IO) {
                logDao.getAllLiveLogs().observeOnce {
                    assertThat("Live logs should be empty but is ${it.size}", it.size, `is`(0))
                }

                val testLog = LogModel(1, "d", "Test", "Test", "", 1, 0, 0.0, 0.0)
                logDao.upsert(testLog)

                logDao.getAllLiveLogs().observeOnce {
                    assertThat("Live Logs should contain 1 log but actually contains ${it.size}", it.size, `is`(1))
                }
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun `on Getting All Logs Should Get Empty List If Table Is Empty`() {
        runBlocking {
            withContext(IO) {
                val noteList: List<LogModel> = logDao.getAllLogs()
                assertThat("Table is not Empty but it should be", noteList.isEmpty(), `is`(true))
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun `on Getting All Live Logs Should Get Empty List If Table Is Empty`() {
        runBlocking {
            withContext(IO) {
                val noteList: List<LogModel> = LiveDataTestUtil.getValue(logDao.getAllLiveLogs())
                assertThat("Table is not Empty but it should be", noteList.isEmpty(), `is`(true))
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun `on Inserting 2 Logs into Log_Table Row Count returned should be 2`() {
        runBlocking {
            withContext(IO) {
                val notesList: List<LogModel> = listOf(testLog1, testLog2)
                logDao.upsertAll(notesList)

                assertThat("Row Count is incorrect", LiveDataTestUtil.getValue(logDao.getAllLiveLogs()).size, `is`(2))
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun `on GetAllLogIDs returns list of IDs`() {
        runBlocking {
            withContext(IO) {
                val notesList: List<LogModel> = listOf(testLog1, testLog2, testLog3, testLog4, testLog5)
                logDao.upsertAll(notesList)

                assertThat("mss", logDao.getALLLogIDs(), `is`(listOf<Long>(1,2, 3, 4, 5)))
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun `on calling getLogByID with non existing ID returns null`() {
        runBlocking {
            withContext(IO) {
                val notesList: List<LogModel> = listOf(testLog1, testLog2, testLog3, testLog4, testLog5)
                logDao.upsertAll(notesList)

                assertThat("mss", logDao.getLogByID(6), `is`(nullValue(null)))
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun `on calling getLogByID with ID returns LogModel with the same ID`() {
        runBlocking {
            withContext(IO) {
                val notesList: List<LogModel> = listOf(testLog1, testLog2, testLog3, testLog4, testLog5)
                logDao.upsertAll(notesList)
                testLog2.logID = 2

                assertThat("mss", logDao.getLogByID(2), `is`(testLog2))
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun `on calling getLogByIDLive with non existing ID returns null`() {
        runBlocking {
            withContext(IO) {
                val notesList: List<LogModel> = listOf(testLog1, testLog2, testLog3, testLog4, testLog5)
                logDao.upsertAll(notesList)

                assertThat("mss", LiveDataTestUtil.getValue(logDao.getLogByIDLive(6)), `is`(nullValue(null)))
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun `on calling getLogByIDLive with ID returns LogModel with the same ID`() {
        runBlocking {
            withContext(IO) {
                val notesList: List<LogModel> = listOf(testLog1, testLog2, testLog3, testLog4, testLog5)
                logDao.upsertAll(notesList)
                testLog1.logID = 1

                assertThat("mss", LiveDataTestUtil.getValue(logDao.getLogByIDLive(1)), `is`(testLog1))
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun `on Updating A Single Log Check If Update Happens Correctly`() {
        runBlocking {
            withContext(IO) {
                //val note:LogModel = FakeNotesSource.fetchFakeNote()
                logDao.upsert(testLog1)
                //note.setNoteTitle(FakeNotesSource.FAKE_NOTE_UPDATED_TITLE)
                testLog1.logID = 1
                testLog1.msg = "Testing ABC"
                logDao.upsert(testLog1)
                assertThat("Updating Logger Failed", LiveDataTestUtil.getValue(logDao.getLogByIDLive(1)).msg, `is`("Testing ABC"))
            }
        }
    }

//    @Test
//    @Throws(Exception::class)
//    fun `on Updating List of Logger Changes using upsert Check If All Logs Are Updated Correctly`() {
//        runBlocking {
//            withContext(IO) {
//                val newDate = LocalDateTime.now().plusDays(1)
//                //val note:LogModel = FakeNotesSource.fetchFakeNote()
//                logDao.upsert(testLog1, testLog2, testLog3)
//                testLog1.logID = 1
//                testLog2.logID = 2
//                testLog3.logID = 3
//                //note.setNoteTitle(FakeNotesSource.FAKE_NOTE_UPDATED_TITLE)
//                testLog1.msg = "Testing ABC"
//                testLog2.tag = "New"
//                testLog3.createDate = newDate
//                logDao.upsert(testLog1, testLog2, testLog3)
//                assertThat("Updating testLog1 Failed", LiveDataTestUtil.getValue(logDao.getLogByIDLive(1)).msg, `is`("Testing ABC"))
//                assertThat("Updating testLog2 Failed", LiveDataTestUtil.getValue(logDao.getLogByIDLive(2)).tag, `is`("New"))
//                assertThat("Updating testLog3 Failed", LiveDataTestUtil.getValue(logDao.getLogByIDLive(3)).createDate, `is`(newDate))
//            }
//        }
//    }

//    @Test
//    @Throws(Exception::class)
//    fun `on Updating List of Logger Changes using upsertAll Check If All Logs Are Updated Correctly`() {
//        runBlocking {
//            withContext(IO) {
//                val newDate = LocalDateTime.now().plusDays(1)
//                //val note:LogModel = FakeNotesSource.fetchFakeNote()
//                logDao.upsert(testLog1, testLog2, testLog3)
//                testLog1.logID = 1
//                testLog2.logID = 2
//                testLog3.logID = 3
//                //note.setNoteTitle(FakeNotesSource.FAKE_NOTE_UPDATED_TITLE)
//                testLog1.msg = "Testing ABC"
//                testLog2.tag = "New"
//                testLog3.createDate = newDate
//                logDao.upsertAll(listOf(testLog1, testLog2, testLog3))
//                assertThat("Updating testLog1 Failed", LiveDataTestUtil.getValue(logDao.getLogByIDLive(1)).msg, `is`("Testing ABC"))
//                assertThat("Updating testLog2 Failed", LiveDataTestUtil.getValue(logDao.getLogByIDLive(2)).tag, `is`("New"))
//                assertThat("Updating testLog3 Failed", LiveDataTestUtil.getValue(logDao.getLogByIDLive(3)).createDate, `is`(newDate))
//            }
//        }
//    }

    @Test
    @Throws(Exception::class)
    fun `on Log Deletion Check If Log Is Deleted From Table`() {
        runBlocking {
            withContext(IO) {
                //val noteList = FakeNotesSource.getFakeNotes(5)
                val notesList: List<LogModel> = listOf(testLog1, testLog2, testLog3, testLog4, testLog5)
                logDao.upsertAll(notesList)

                testLog3.logID = 3

                logDao.deleteLog(testLog3)

                assertThat("Logger was not deleted correctly", LiveDataTestUtil.getValue(logDao.getLogByIDLive(3)), nullValue(null))
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun `on Log Deletion Check If Correct Count Is Returned`() {
        runBlocking {
            withContext(IO) {
                //val noteList = FakeNotesSource.getFakeNotes(5)
                val notesList: List<LogModel> = listOf(testLog1, testLog2, testLog3, testLog4, testLog5)
                logDao.upsertAll(notesList)

                testLog3.logID = 3//Must be set so that deleteLog knows which ID to delete

                val numDeleted = logDao.deleteLog(testLog3)

                assertThat("Numbered returned from Logger table deletion is not correct", numDeleted, `is`(1))
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun `on Log Delete All Check If table is empty`() {
        runBlocking {
            withContext(IO) {
                //val noteList = FakeNotesSource.getFakeNotes(5)
                val notesList: List<LogModel> = listOf(testLog1, testLog2, testLog3, testLog4, testLog5)
                logDao.upsertAll(notesList)

                logDao.deleteAll()

                assertThat("Table should be empty after Delete all is called", LiveDataTestUtil.getValue(logDao.getAllLiveLogs()).size, `is`(0))
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun `on Log Delete All Check Row Count returned is total number of rows deleted`() {
        runBlocking {
            withContext(IO) {
                //val noteList = FakeNotesSource.getFakeNotes(5)
                val notesList: List<LogModel> = listOf(testLog1, testLog2, testLog3, testLog4, testLog5)
                logDao.upsertAll(notesList)

                val numDeleted = logDao.deleteAll()

                assertThat("Numbered returned from Logger table deletion is not correct", numDeleted, `is`(5))
            }
        }
    }

//    @Test
//    @Throws(Exception::class)
//    fun `on deleteAllLogsOlderThan 7 Days verify number of logs deleted are correct`() {
//        runBlocking {
//            withContext(IO) {
//                //val noteList = FakeNotesSource.getFakeNotes(5)
//                testLog1.createDate = LocalDateTime.now().minusDays(9)
//                testLog2.createDate = LocalDateTime.now().minusDays(8)
//                testLog3.createDate = LocalDateTime.now().minusDays(7)
//                testLog4.createDate = LocalDateTime.now().minusDays(6)
//                val notesList: List<LogModel> = listOf(testLog1, testLog2, testLog3, testLog4, testLog5)
//                logDao.upsertAll(notesList)
//
//                val numDeleted = logDao.deleteAllLogsOlderThan(7)
//
//                assertThat("Numbered returned from Logger table deletion is not correct", numDeleted, `is`(3))
//            }
//        }
//    }

//    @Test
//    @Throws(Exception::class)
//    fun `on deleteAllLogsOlderThan 7 Days verify number of logs remaining are correct`() {
//        runBlocking {
//            withContext(IO) {
//                //val noteList = FakeNotesSource.getFakeNotes(5)
//                testLog1.createDate = LocalDateTime.now().minusDays(9)
//                testLog2.createDate = LocalDateTime.now().minusDays(8)
//                testLog3.createDate = LocalDateTime.now().minusDays(7)
//                testLog4.createDate = LocalDateTime.now().minusDays(6)
//                val notesList: List<LogModel> = listOf(testLog1, testLog2, testLog3, testLog4, testLog5)
//                logDao.upsertAll(notesList)
//
//                logDao.deleteAllLogsOlderThan(7)
//
//                testLog4.logID = 4
//                testLog5.logID = 5
//                val notesListRemaining: List<LogModel> = listOf(testLog4, testLog5)
//                assertThat("Numbered returned from Logger table deletion is not correct", LiveDataTestUtil.getValue(logDao.getAllLiveLogs()), `is`(notesListRemaining))
//            }
//        }
//    }

    @Test
    @Throws(Exception::class)
    fun `on ResetIndex Verify That The Index Is Actually Reset`() {
        runBlocking {
            withContext(IO) {
                val notesList: List<LogModel> = listOf(testLog1, testLog2, testLog3, testLog4, testLog5)
                logDao.upsertAll(notesList)

                logDao.deleteAll()
                logDao.resetIndex()

                logDao.upsertAll(notesList)

                assertThat("", logDao.getALLLogIDs(), `is`(listOf<Long>(1,2,3,4,5)))
            }
        }
    }

}



package com.mycomp.mytestapp

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import android.content.Context
//import androidx.room.Room
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.mycomp.mytestapp.data.MYTESTAPPRoomDatabase
//import com.mycomp.mytestapp.data.observeOnce
//import com.mycomp.mytestapp.utilities.log.db.LogDao
//import com.mycomp.mytestapp.utilities.log.db.LogModel
//import junit.framework.Assert.*
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
//@RunWith(AndroidJUnit4::class)
//class ExampleInstrumentedTest {
//
//    private lateinit var db: MYTESTAPPRoomDatabase
//    private lateinit var store: LogDao
//
//    @Before
//    fun setUp() {
//        db = MYTESTAPPRoomDatabase.getDatabaseInstance(ApplicationProvider.getApplicationContext<Context>(), true)
//        store = db.logDao()
//    }
//
//    @After
//    fun tearDown() {
//        db.close()
//    }
//
//    @Test
//    fun logTest() {
//        assertEquals(0, store.getAllLogs().size)
//
//        val testLog = LogModel(1,"Test", "Test","", 1, 0, 0.0, 0.0)
//        val testLog1 = LogModel(2,"Test", "Test","", 1, 0, 0.0, 0.0)
//
//        assertNotNull(testLog.logID)
//
//        var testLogList = store.upsert(testLog)
//        assertNotNull(testLogList)
//        assertEquals(testLogList.count(), 1)
//
//        testLogList = store.upsert(testLog, testLog1)
//        assertNotNull(testLogList)
//        assertEquals(testLogList.count(), 2)
//
//        val result = store.findById(3)
//
//        assertNotNull(result)
//        assertEquals(result.deviceID, 1)
//
//        assertTrue(testLog == testLog1)
//
//        store.deleteLog(result)
//        assertEquals(2, store.getAllLogs().size)
//    }
//}
//
//@RunWith(AndroidJUnit4::class)
//class PostDaoReadWriteTest {
//    private lateinit var postDao: LogDao
//    private lateinit var db: MYTESTAPPRoomDatabase
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Before
//    fun createDb() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        db = Room.inMemoryDatabaseBuilder(
//            context, MYTESTAPPRoomDatabase::class.java).build()
//        postDao = db.logDao()
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun writePostAndReadInList() {
//        postDao.getAllLiveLogs().observeOnce {
//            assertEquals(0, it.size)
//        }
//
//        //assertEquals(0, postDao.getAllLiveLogs().value?.size)
//        //val post = TestUtil.createPost(id=42)
//        val testLog = LogModel(1,"Test", "Test","", 1, 0, 0.0, 0.0)
//        postDao.upsert(testLog)
//
//        postDao.getAllLiveLogs().observeOnce {
//            assertEquals(1, it.size)
//        }
//
//        //assertEquals(1, postDao.getAllLiveLogs().value?.size)
//    }
//}

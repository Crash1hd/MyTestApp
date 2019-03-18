package com.mycomp.mytestapp.utilities

//@RunWith(AndroidJUnit4::class)
//class ExampleUnitTest {
//
//    //setup db
//    private lateinit var testAppDatabase: MYTESTAPPRoomDatabase
//    private lateinit var logDao: LogDao
//
//    @Before
//    fun setUp() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
//
//        try {
//            //GlobalScope.launch {
//                //testAppDatabase = Room.inMemoryDatabaseBuilder(context, MYTESTAPPRoomDatabase::class.java).build()
//                //logDao = testAppDatabase.logDao()
//            //}
//            testAppDatabase =
//                Room.inMemoryDatabaseBuilder(context, MYTESTAPPRoomDatabase::class.java).allowMainThreadQueries().build()
//            logDao = testAppDatabase.logDao()
//
//        } catch (e: java.lang.Exception) {
//            Log.e("Test","Error", e)
//        }
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        testAppDatabase.close()
//    }
//
//    @Test
//    fun logTest() {
//        //GlobalScope.launch {
//            Assert.assertEquals(0, logDao.getAllLogs().size)
//
//            val testLog = LogModel("Test", "Test", 1, 0, 0.0, 0.0)
//            val testLog1 = LogModel("Test", "Test", 1, 0, 0.0, 0.0)
//
//            assertNotNull(testLog.logID)
//
//            var testLogList = logDao.upsert(testLog)
//            assertNotNull(testLogList)
//            Assert.assertEquals(testLogList.count(), 1)
//
//            testLogList = logDao.upsert(testLog, testLog1)
//            assertNotNull(testLogList)
//            Assert.assertEquals(testLogList.count(), 2)
//
//            val result = logDao.findById(3)
//
//            assertNotNull(result)
//            Assert.assertEquals(result.deviceID, 1)
//
//            Assert.assertTrue(testLog == testLog1)
//
//            logDao.delete(result)
//            Assert.assertEquals(2, logDao.getAllLogs().size)
//       // }
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun `log Database is Empty on Start`() {
//        //GlobalScope.launch {
//            //assertEquals(null, logDao.getAllLogs().value?.size)
//
//            val testLog = LogModel("Test", "Test", 0, 0, 0.0, 0.0)
//            //val testLog1 = LogModel("Test1", "Test1", 0, 0, 0.0, 0.0)
//            // When inserting a task
//            //var testLogList = logDao.upsert(testLog)
//            logDao.upsertAll(testLog)
//            //testAppDatabase.logDao().upsertAll(testLog1)
//
//            //When getting the task by id from the database
//            val loaded = logDao.getLogByID("1")
//            assertNotNull(logDao.getLogByID("1"))
//            println(loaded)
//
//
//            //assertEquals(null, logDao.getAllLogs().value?.size)
//
////        val testLog = LogModel("Test", "Test", 0, 0, 0.0, 0.0)
////        logDao.upsertAll(testLog)
////        Thread.sleep(20000)
////        assertEquals(1, logDao.getAllLogs().value?.size)
//
////        val post = TestUtil.createPost(id=42)
////        postDao.upsert(post)
////        assertEquals(1, postDao.getAll().value?.size)
//
////        Google's Example https://developer.android.com/training/data-storage/room/testing-db
////        val user: User = TestUtil.createUser(3).apply {
////            setName("george")
////        }
////        userDao.upsert(user)
////        val byName = userDao.findUsersByName("george")
////        assertThat(byName.get(0), equalTo(user))
//       // }
//    }
//
//    @Test
//    fun `log i does not return null`() {
//        assertNotNull("LogModel e has returned null", Log.i("LogModel", "Test", Exception("Test")))
//    }
//
//    @Test
//    fun `log e does not return null`() {
//        assertNotNull("LogModel e has returned null", Log.e("LogModel", "Test", Exception("Test")))
//    }
//
//    @Test
//    fun `log v does not return null`() {
//        assertNotNull("LogModel v has returned null", Log.v("LogModel", "Test", Exception("Test")))
//    }
//
//    @Test
//    fun `log d does not return null`() {
//        assertNotNull("LogModel d has returned null", Log.d("LogModel", "Test", Exception("Test")))
//    }
//
//    @Test
//    fun `log w does not return null`() {
//        assertNotNull("LogModel w has returned null", Log.w("LogModel", "Test", Exception("Test")))
//    }
//
////    @Test
////    fun `log i saves into logDatabase`() {
////        val cachedBuffer = BufferFactory.makeCachedBuffer()
////        buffersDatabase.cachedBufferDao().insertBuffer(cachedBuffer)
////
////        val buffers = buffersDatabase.cachedBufferDao().getBuffers()
////        assert(buffers.isNotEmpty())
////    }
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Test
//    @Throws(Exception::class)
//    fun writePostAndReadInList() {
//        logDao.getAllLiveLogs().observeOnce {
//            Assert.assertEquals(0, it.size)
//        }
//
//        //assertEquals(0, postDao.getAllLiveLogs().value?.size)
//        //val post = TestUtil.createPost(id=42)
//        val testLog = LogModel("Test", "Test", 1, 0, 0.0, 0.0)
//        logDao.upsert(testLog)
//
//        logDao.getAllLiveLogs().observeOnce {
//            Assert.assertEquals(1, it.size)
//        }
//
//        //assertEquals(1, postDao.getAllLiveLogs().value?.size)
//    }
//}



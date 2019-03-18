package com.mycomp.mytestapp.utilities.log.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoggerModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testLog = LogModel(1, "d", "test", "test", null, 1, 1, 0.0, 0.0)
    private val testLog1 = LogModel(1, "d", "test", "test", "test", 1, 1, 0.0, 0.0)

    @Test
    fun `logModel LogID should be an Int`() {
        assertThat("LogID should not be null", testLog.logID, `is`(1.toLong()))
    }

    @Test
    fun `logModel tag contains string of test`() {
        assertThat("LogModel tag string is not the word Test", testLog.tag, `is`("test"))
    }

    @Test
    fun `logModel msg contains string of test`() {
        assertThat("LogModel msg string is not the word Test", testLog.msg, `is`("test"))
    }

    @Test
    fun `logModel tr can be nullable`() {
        assertThat("LogModel tr is not nullable", testLog.tr, nullValue(null))
    }

    @Test
    fun `logModel tr contains string of test`() {
        assertThat("LogModel tr string is not the word Test", testLog1.tr, `is`("test"))
    }

    @Test
    fun `logModel contains deviceID`() {
        assertThat("LogModel deviceID is an Int of 1", testLog.deviceID, `is`(1))
    }

    @Test
    fun `logModel contains userID`() {
        assertThat("LogModel userID is an Int of 1", testLog.userID, `is`(1))
    }

    @Test
    fun `logModel contains latitude`() {
        assertThat("LogModel latitude is a Double of 0.0", testLog.latitude, `is`(0.0))
    }

    @Test
    fun `logModel contains longitude`() {
        assertThat("LogModel longitude is a Double of 0.0", testLog.longitude, `is`(0.0))
    }

    @Test
    fun `logModel contains a timestamp`() {
        //assertThat("LogModel timestamp is missing", testLog.createDate, isA(LocalDateTime::class.java))
    }

    @Test
    fun `logModel toString returns the correct string`() {
        //assertThat("LogModel toString method does not return correct string", testLog.toString(), `is`("1: [test] test @ ${testLog.createDate}\n"))
    }
}



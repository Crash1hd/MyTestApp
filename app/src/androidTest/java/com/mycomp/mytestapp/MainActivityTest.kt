package com.mycomp.mytestapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mycomp.mytestapp.ui.MainActivity
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mainActivityTestRule:ActivityTestRule<MainActivity> = ActivityTestRule<MainActivity>(
        MainActivity::class.java)

    private var mainActivity: MainActivity? = null

    @Before
    fun setUp() {
        mainActivity = mainActivityTestRule.activity
    }

    @After
    fun tearDown() {
        mainActivity = null
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        Assert.assertEquals("com.mycomp.mytestapp", appContext.packageName)
    }

    @Test
    fun testLaunch() {
        onView(withId(R.id.hWorld)).check(ViewAssertions.matches(isDisplayed()))
        onView(withText("Hello World!")).check(ViewAssertions.matches(isDisplayed()))
    }
}
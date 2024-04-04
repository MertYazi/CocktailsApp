package com.example.cocktailsapp

import android.view.View
import android.view.ViewGroup
import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.base.DefaultFailureHandler
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.example.cocktailsapp.di.idlingResource
import junit.framework.AssertionFailedError
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.util.Locale
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
abstract class BaseUITest {

    //Running count of the number of Android Not Responding dialogues to prevent endless dismissal.
    private var AnrCount = 0
    //`RootViewWithoutFocusException` class is private, need to match the message (instead of using type matching).
    private val rootViewWithoutFocusExceptionMsg = java.lang.String.format(
        Locale.ROOT,
        "Waited for the root of the view hierarchy to have "
                + "window focus and not request layout for 10 seconds. If you specified a non "
                + "default root matcher, it may be picking a root that never takes focus. "
                + "Root:")


    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(idlingResource)
        Espresso.setFailureHandler { error, viewMatcher ->
            if (error.message!!.contains(rootViewWithoutFocusExceptionMsg) && AnrCount < 3) {
                AnrCount++
                handleAnrDialogue()
            } else { // chain all failures down to the default espresso handler
                DefaultFailureHandler(InstrumentationRegistry.getInstrumentation().context).handle(error, viewMatcher)
            }
        }
    }

    private fun handleAnrDialogue() {
        val device = UiDevice.getInstance(getInstrumentation())
        // If running the device in English Locale
        val waitButton = device.findObject(UiSelector().textContains("wait"))
        if (waitButton.exists()) waitButton.click()
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == view)
            }
        }
    }

    fun ViewInteraction.waitUntilVisible(timeout: Long): ViewInteraction {
        val startTime = System.currentTimeMillis()
        val endTime = startTime + timeout

        do {
            try {
                check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                return this
            } catch (e: AssertionFailedError) {
                Thread.sleep(50)
            }
        } while (System.currentTimeMillis() < endTime)

        throw TimeoutException()
    }
}
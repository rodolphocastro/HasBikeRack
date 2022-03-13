package com.ardc.hasbikerack

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {
    /**
     * Instrumenting a Rule based on the App's MainActivity.
     * This is needed because our SUT is part of the MainActivity's hierarchy.
     */
    @get:Rule
    val mainActivityTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Given an User is logged in
     * When I open up the App
     * Then the Sign out button exists
     */
    @Test
    fun givenAnUserIsLoggedWhenIOpenTheAppThenSignOutButtonExists() {
        // Arrange
        val subject = mainActivityTestRule.activity

        subject.currentUser?.let {
            // Act
            val got = mainActivityTestRule
                .onNodeWithText(subject.getString(R.string.signout_button_text))

            // Assert
            got.assertExists()
        }
    }

    /**
     * Given an User is not logged in
     * When I open up the App
     * Then the Sign In button exists
     */
    @Test
    fun givenNoUserIsLoggedInWhenIOpenTheAppThenTheSignInButtonExists() {
        // Arrange
        val subject = mainActivityTestRule.activity

        if (subject.currentUser == null) {
            // Act
            val got = mainActivityTestRule
                .onNodeWithText(subject.getString(R.string.common_signin_button_text))

            // Assert
            got.assertExists()
        }
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.ardc.hasbikerack", appContext.packageName)
    }
}
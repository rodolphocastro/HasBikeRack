package com.ardc.hasbikerack

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ardc.hasbikerack.ui.theme.HasBikeRackTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 * Also see [Jetpack compose testing](https://developer.android.com/jetpack/compose/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    /**
     * Instrumenting a Rule based on the App's MainActivity.
     * This is needed because our SUT is part of the MainActivity's hierarchy.
     */
    @get:Rule
    val mainActivityTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.ardc.hasbikerack", appContext.packageName)
    }


    /**
     * Given a fresh start
     * When the App loads
     * Then a greeting is displayed
     */
    @Test
    fun freshStart_loads_displaysText() {
        // Arrange
        mainActivityTestRule.setContent {
            HasBikeRackTheme {
                Greeting(name = "Tobias")
            }
        }

        // Act
        val got = mainActivityTestRule.onNodeWithText("Hello Tobias!", ignoreCase = true, substring = true)

        // Assert
        got.assertTextContains("Tobias", substring = true, ignoreCase = true)
    }
}
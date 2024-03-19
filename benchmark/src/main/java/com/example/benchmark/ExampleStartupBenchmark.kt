package com.example.benchmark

import androidx.benchmark.macro.*
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiObject2
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = "com.masterproject.jetpackandmacrobenchmark",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }

    @Test
    fun scrollAndNavigate() = benchmarkRule.measureRepeated(
        packageName = "com.masterproject.jetpackandmacrobenchmark",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()

        addElementsAndScrollDown()
    }
}

fun MacrobenchmarkScope.addElementsAndScrollDown() {
    val maxAttempts = 5
    var attempt = 0
    var button: UiObject2? = null

    while (button == null && attempt < maxAttempts) {
        button = device.findObject(By.text("EXPLORE FLOORS"))
        if (button == null) {
            Thread.sleep(500) // Wait for 500 milliseconds before next attempt
            attempt++
        }
    }

    if (button != null) {
        // Button found, proceed with the click operation
        repeat(5) {
            button.click()
        }
    } else {
        // Button not found after multiple attempts, handle the case appropriately
        println("Button not found after $maxAttempts attempts.")
    }
}

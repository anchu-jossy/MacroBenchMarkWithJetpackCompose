package com.example.baselineprofile

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This test class benchmarks the speed of app startup.
 * Run this benchmark to verify how effective a Baseline Profile is.
 * It does this by comparing [CompilationMode.None], which represents the app with no Baseline
 * Profiles optimizations, and [CompilationMode.Partial], which uses Baseline Profiles.
 *
 * Run this benchmark to see startup measurements and captured system traces for verifying
 * the effectiveness of your Baseline Profiles. You can run it directly from Android
 * Studio as an instrumentation test, or run all benchmarks for a variant, for example benchmarkRelease,
 * with this Gradle task:
 * ```
 * ./gradlew :baselineprofile:connectedBenchmarkReleaseAndroidTest
 * ```
 *
 * You should run the benchmarks on a physical device, not an Android emulator, because the
 * emulator doesn't represent real world performance and shares system resources with its host.
 *
 * For more information, see the [Macrobenchmark documentation](https://d.android.com/macrobenchmark#create-macrobenchmark)
 * and the [instrumentation arguments documentation](https://d.android.com/topic/performance/benchmarking/macrobenchmark-instrumentation-args).
 **/
@RunWith(AndroidJUnit4::class)
@LargeTest
class StartupBenchmarks {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startupCold() = benchmarkRule.measureRepeated(
        packageName = "com.masterproject.jetpackandmacrobenchmark",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }
    @Test
    fun startupWarm() = benchmarkRule.measureRepeated(
        packageName = "com.masterproject.jetpackandmacrobenchmark",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.WARM
    ) {
        pressHome()
        startActivityAndWait()
    }
    @Test
    fun startupHot() = benchmarkRule.measureRepeated(
        packageName = "com.masterproject.jetpackandmacrobenchmark",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.HOT
    ) {
        pressHome()
        startActivityAndWait()
    }


    @Test
    fun LoadAndScroll() = benchmarkRule.measureRepeated(
        packageName = "com.masterproject.jetpackandmacrobenchmark",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()

        addElementsAndScrollDown()


    }


    @Test
    fun navigate() = benchmarkRule.measureRepeated(
        packageName = "com.masterproject.jetpackandmacrobenchmark",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()

        addElementsAndScrollDown()
        device.wait(Until.hasObject(By.text("Floor 13")), 8000)
        device.findObject(By.text("Floor 13")).click()

    }

}

@Test
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
        repeat(15) {
            button.click()
        }
    } else {
        // Button not found after multiple attempts, handle the case appropriately
        println("Button not found after $maxAttempts attempts.")

    }


}
package com.bbcnews.utils

import com.bbcnews.config.ConfigFactory
import com.bbcnews.utils.AppiumServer.appiumService
import io.appium.java_client.AppiumBy
import io.appium.java_client.AppiumDriver
import io.appium.java_client.remote.AndroidMobileCapabilityType
import io.appium.java_client.remote.MobileCapabilityType
import org.junit.After
import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.PointerInput
import org.openqa.selenium.interactions.Sequence
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.*
import java.time.Duration
import java.util.concurrent.Semaphore

object DriverManager {
    private var driver: AppiumDriver? = null
    private val driverInitializedSemaphore = Semaphore(0)
    fun getDriver(): AppiumDriver {
        if (driver == null) {
            createDriver("Pixel 6")
        }
        return driver as AppiumDriver
    }

    fun createDriver(deviceName: String) {
        AppiumServer.startServer()

        Thread.sleep(5000)
        val config = ConfigFactory.getConfig(deviceName)

        Runtime.getRuntime().exec("adb install ${config.appPath}")
        val caps = DesiredCapabilities()
        caps.setCapability("avd","Pixel_6_Pro_API_33");
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android")
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, config.deviceName)
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, config.platformVersion)
        caps.setCapability(MobileCapabilityType.APP, config.appPath)
        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, config.appPackage)
        caps.setCapability(MobileCapabilityType.NO_RESET, false)
        caps.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
        caps.setCapability(MobileCapabilityType.FULL_RESET, false)
        caps.setCapability(MobileCapabilityType.UDID, config.udid)
        caps.setCapability("autoGrantPermissions", true)
        caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60)


        val serverUrl = appiumService!!.url

        driver = AppiumDriver(serverUrl, caps)
        val wait = FluentWait(driver)
            .withTimeout(Duration.ofSeconds(40))
            .pollingEvery(Duration.ofMillis(5000))
            .ignoring(NoSuchElementException::class.java)

        wait.until { driver != null }

        driverInitializedSemaphore.release()

        clickConsentPopup(driver!!)
        handlePopups(driver!!)
    }

    private fun handlePopups(driver: AppiumDriver) {
        val expression = "new UiSelector().className(\"android.widget.Button\").index(1)"
        Thread.sleep(5000)
        WaitUtils.waitUntilElementIsVisible(AppiumBy.className("android.widget.Button"), 10)
        val webView = WaitUtils.findElementByUiAutomator(driver, expression)

        val popupTimeout = 10 // Maximum number of seconds to wait for a popup

        var popupDisplayed = true
        var timeout = 0

        while (popupDisplayed && timeout < popupTimeout) {
            try {
                if (webView.isDisplayed) {
                    webView.click()
                }
            } catch (e: NoSuchElementException) {
                popupDisplayed = false
            }
            catch (e: StaleElementReferenceException) {
                popupDisplayed = false
            }
            Thread.sleep(1000) // Wait for 1 second before checking again
            timeout++
        }
    }

    private fun clickConsentPopup(driver: AppiumDriver) {
        val expression = "new UiSelector().className(\"android.widget.Button\").index(0)"
        Thread.sleep(5000)
        WaitUtils.waitUntilElementIsVisible(AppiumBy.className("android.widget.Button"), 10)
        val webView = WaitUtils.findElementByUiAutomator(driver, expression)
        Thread.sleep(5000)
        if (webView.isDisplayed) {
            webView.click()
        }
    }


    object ScrollUtils {
        fun scrollDown(
            driver: AppiumDriver,
            duration: Duration = Duration.ofMillis(500),
            startPercentage: Double = 0.8,
            endPercentage: Double = 0.2
        ) {
            val size = driver.manage().window().size
            val startX = size.width / 2
            val startY = (size.height * startPercentage).toInt()
            val endY = (size.height * endPercentage).toInt()

            val pi = PointerInput(PointerInput.Kind.TOUCH, "finger1")
            val down = pi.createPointerMove(
                duration,
                PointerInput.Origin.viewport(),
                org.openqa.selenium.Point(startX, startY)
            )
            val up = pi.createPointerMove(
                duration,
                PointerInput.Origin.viewport(),
                org.openqa.selenium.Point(startX, endY)
            )
            val swipe = Sequence(pi, 0)
            swipe.addAction(down)
            swipe.addAction(pi.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
            swipe.addAction(up)
            swipe.addAction(pi.createPointerUp(PointerInput.MouseButton.LEFT.asArg()))
            driver.perform(listOf(swipe))
        }

        fun scrollDownToEnd(driver: AppiumDriver, duration: Duration = Duration.ofMillis(500)) {
            val size = driver.manage().window().size
            val startX = size.width / 2
            val startY = (size.height * 0.8).toInt()
            val endY = (size.height * 0.2).toInt()
            val scrollDuration = duration.dividedBy(10) // Split the duration equally between the two scroll actions

            val pi = PointerInput(PointerInput.Kind.TOUCH, "finger1")
            val down = pi.createPointerMove(
                scrollDuration,
                PointerInput.Origin.viewport(),
                org.openqa.selenium.Point(startX, startY)
            )
            val up = pi.createPointerMove(
                scrollDuration,
                PointerInput.Origin.viewport(),
                org.openqa.selenium.Point(startX, endY)
            )
            val swipeDown = Sequence(pi, 0)
            swipeDown.addAction(down)
            swipeDown.addAction(pi.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
            swipeDown.addAction(up)
            swipeDown.addAction(pi.createPointerUp(PointerInput.MouseButton.LEFT.asArg()))

            val startTime = System.currentTimeMillis()
            val endTime = startTime + duration.toMillis()

            //Perform the scroll action multiple times until the end of the screen is reached
            while (System.currentTimeMillis() < endTime) {
                driver.perform(listOf(swipeDown))
            }
        }

        private fun isEndOfScreenReached(driver: AppiumDriver): Boolean {
            val size = driver.manage().window().size
            val endY = (size.height * 0.1).toInt() // Adjust the end position threshold as needed
            val expression = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().index(0).instance(0))"
            val element = driver.findElement(AppiumBy.ByAndroidUIAutomator(expression))
            val location = element.location.y
            return location <= endY
        }
    }
        object WaitUtils {

        private const val DEFAULT_WAIT_TIME_SECONDS = 10L
            fun findElementByUiAutomator(driver: AppiumDriver, expression: String): WebElement {
                return driver.findElement(AppiumBy.ByAndroidUIAutomator(expression))
            }

        fun waitUntilElementIsVisible(locator: By, timeoutSeconds: Long): WebElement {
            val wait = FluentWait(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException::class.java)

            val condition = ExpectedConditions.visibilityOfElementLocated(locator)
            return wait.until(condition) ?: throw NoSuchElementException("Element not found: $locator")
        }

        /**
         * Waits for an element to be present in the DOM of a page.
         * @param driver The Appium driver to use.
         * @param locator The locator for the element.
         * @param waitTimeSeconds The time to wait in seconds. Defaults to 10 seconds.
         * @return The first matching MobileElement once it is located.
         */
        fun waitForElement(
            driver: AppiumDriver,
            element: WebElement,
            waitTimeSeconds: Long = DEFAULT_WAIT_TIME_SECONDS
        ): WebElement {
            val wait = FluentWait(driver)
                .withTimeout(Duration.ofSeconds(waitTimeSeconds))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException::class.java)
            return wait.until(ExpectedConditions.visibilityOf(element)) as WebElement
        }

        /**
         * Waits for an element to be visible on the page.
         * @param driver The Appium driver to use.
         * @param element The element to wait for.
         * @param waitTimeSeconds The time to wait in seconds. Defaults to 10 seconds.
         * @return The same MobileElement once it is visible.
         */
        fun waitForVisibility(
            driver: AppiumDriver,
            element: WebElement,
            waitTimeSeconds: Long = DEFAULT_WAIT_TIME_SECONDS
        ): WebElement {
            val wait = FluentWait(driver)
                .withTimeout(Duration.ofSeconds(waitTimeSeconds))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException::class.java)
            return wait.until(ExpectedConditions.visibilityOf(element)) as WebElement
        }

        /**
         * Waits for an element to be clickable.
         * @param driver The Appium driver to use.
         * @param element The element to wait for.
         * @param waitTimeSeconds The time to wait in seconds. Defaults to 10 seconds.
         * @return The same MobileElement once it is clickable.
         */
        fun waitForClickable(
            driver: AppiumDriver,
            element: WebElement,
            waitTimeSeconds: Long = DEFAULT_WAIT_TIME_SECONDS
        ): WebElement {
            val wait = FluentWait(driver)
                .withTimeout(Duration.ofSeconds(waitTimeSeconds))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException::class.java)
            return wait.until(ExpectedConditions.elementToBeClickable(element)) as WebElement
        }

        fun waitForTextToBePresent(
            driver: AppiumDriver,
            element: WebElement,
            text: String,
            waitTimeSeconds: Long = DEFAULT_WAIT_TIME_SECONDS
        ): Boolean {
            val wait = FluentWait(driver)
                .withTimeout(Duration.ofSeconds(waitTimeSeconds))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(org.openqa.selenium.NoSuchElementException::class.java)

            return wait.until(ExpectedConditions.textToBePresentInElement(element, text)) as Boolean
        }
    }

    @After

    fun quitDriver() {
            driver?.quit()
            driver = null
    }
}


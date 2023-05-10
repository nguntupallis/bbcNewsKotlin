package com.bbcnews.pages

import com.bbcnews.config.ConfigFactory
import com.bbcnews.utils.DriverManager.WaitUtils.findElementByUiAutomator
import io.appium.java_client.AppiumBy
import io.appium.java_client.AppiumDriver
import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.FluentWait
import java.time.Duration

class HomePage(private val driver: AppiumDriver?, private val deviceName: String) {
    private val config = ConfigFactory.getConfig("Pixel 6")
    private val appPackage = config.appPackage
    val wait = FluentWait(driver)
        .withTimeout(Duration.ofSeconds(40))
        .pollingEvery(Duration.ofMillis(5000))
        .ignoring(NoSuchElementException::class.java)

    val videoTabElement = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.ByAccessibilityId("Video")))
    val topStoriesTabElement = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.ByAccessibilityId("Top Stories")))
    val myNewsTabElement = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.ByAccessibilityId("My News")))
    val popularTabElement = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.ByAccessibilityId("Popular")))
    val searchIcon = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.ByAccessibilityId("Search")))

    fun isHomePageDisplayed(): Boolean {
        val titleElement = findElementByUiAutomator(driver!!, "resourceId(\"bbc.mobile.news.ww:id/logo\")")
        return titleElement.isDisplayed
    }

    fun getTopStories(): List<WebElement> {
        return driver?.findElements(AppiumBy.ByAccessibilityId("Top Stories")) ?: emptyList()
    }

    fun search(query: String) {
        val searchBox: WebElement? = driver?.findElement(By.id("search_box"))
        searchBox?.sendKeys(query)
        searchBox?.submit()
    }

    fun tapVideoTab() {
        videoTabElement.click()
    }

    fun tapSearchIcon() {
        searchIcon.click()
    }

    fun tapTopStoriesTab() {
        topStoriesTabElement.click()
    }

    fun tapMyNewsTab() {
        myNewsTabElement.click()
    }

    fun tapPopularTab() {
        popularTabElement.click()
    }

    fun getFooterLinks(): List<WebElement> {
        return driver?.findElements(By.id("footer_links")) ?: emptyList()
    }
}

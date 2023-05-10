package com.bbcnews.pages

import com.bbcnews.utils.DriverManager
import com.bbcnews.utils.DriverManager.WaitUtils.findElementByUiAutomator
import com.bbcnews.utils.DriverManager.WaitUtils
import io.appium.java_client.AppiumDriver
import io.appium.java_client.pagefactory.AndroidFindBy
import org.openqa.selenium.WebElement

class VideoPage(private val driver: AppiumDriver) {
    @AndroidFindBy(id = "com.bbc.mobile.news.ww:id/video_tab")
    private lateinit var videoTab: WebElement
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='BBC News Channel']")
    private lateinit var bbcNewsChannelText: WebElement

    fun tapVideoTab() {
        videoTab.click()
    }

    fun isBBCNewsChannelDisplayed(): Boolean {
        return bbcNewsChannelText.isDisplayed
    }

    fun isCopyrightTextDisplayed(): Boolean {
        val expression = "new UiSelector().text(\"Copyright © 2018 BBC\")"
        val copyrightText = findElementByUiAutomator(driver, expression)
        WaitUtils.waitForTextToBePresent(driver, copyrightText, "2018 BBC")
        return copyrightText.isDisplayed
    }
}

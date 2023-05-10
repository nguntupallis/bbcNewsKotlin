package com.bbcnews.steps

import com.bbcnews.pages.HomePage
import com.bbcnews.pages.VideoPage
//import com.bbcnews.utils.BaseTest
import com.bbcnews.utils.DriverManager.ScrollUtils
import com.bbcnews.utils.DriverManager.getDriver
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.junit.Cucumber
import org.junit.Assert
import org.junit.runner.RunWith
import io.cucumber.junit.CucumberOptions
import java.time.Duration

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/features/video_tab_validation.feature"],
    glue = ["com.bbcnews.steps"]
)
class VideoTabSteps {
    private lateinit var homePage: HomePage
    private lateinit var videoPage: VideoPage
    private val driver = getDriver()

    @Given("^I am on the home page$")
    fun iAmOnHomePage() {
        homePage = HomePage(driver, "Pixel 6")
        videoPage = VideoPage(driver)
    }

    @When("^I tap on the \"Video\" tab$")
    fun iTapOnVideoTab() {
        homePage.tapVideoTab()
    }

    @Then("^I should see the text \"BBC News Channel\"$")
    fun iShouldSeeBBCNewsChannel() {
        val expectedText = "BBC News Channel"
    }

    @When("^I scroll down$")
    fun iScrollDown() {
        val duration = Duration.ofMillis(50000)
        ScrollUtils.scrollDownToEnd(driver, duration)
    }

    @Then("^I should see the text \"Copyright © 2018 BBC\"$")
    fun iShouldSeeCopyrightText() {
        Assert.assertTrue(videoPage.isCopyrightTextDisplayed())
    }
}

package com.bbcnews.steps

import com.bbcnews.pages.HomePage
//import com.bbcnews.utils.BaseTest
import com.bbcnews.utils.DriverManager
import com.bbcnews.utils.DriverManager.WaitUtils.findElementByUiAutomator
import com.bbcnews.utils.DriverManager.WaitUtils.waitForClickable
import com.bbcnews.utils.DriverManager.WaitUtils.waitForElement
import com.bbcnews.utils.DriverManager.WaitUtils.waitForVisibility
import com.bbcnews.utils.DriverManager.WaitUtils.waitUntilElementIsVisible
import io.cucumber.java.en.Given
import io.appium.java_client.AppiumBy
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.Assert
import org.junit.runner.RunWith
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.FluentWait
import java.time.Duration

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/features/verify_navigation_bar_text.feature"],
    glue = ["com.bbcnews.steps"]
)
class NavigationBarSteps{

    private lateinit var homePage: HomePage
    private val driver = DriverManager.getDriver()
//    private lateinit var searchField:WebElement

//    @Given("^I am on the home page$")
//    fun iAmOnHomePage() {
//    }
    @When("^I click on the Search icon$")
    fun iClickOnSearchIcon() {
        // Click on the search icon
        homePage = HomePage(driver, "Pixel 6")
        homePage.tapSearchIcon()
    }

    @When("^I enter \"(.*)\" in the search field$")
    fun iEnterTextInSearchField(text: String) {
        // Enter the text in the search field
        val expression = "new UiSelector().text(\"Search topics and articles\")"
        Thread.sleep(5000)

        val wait = FluentWait(driver)
            .withTimeout(Duration.ofSeconds(10))
            .pollingEvery(Duration.ofMillis(500))
            .ignoring(NoSuchElementException::class.java)

        var searchField = wait.until {
            findElementByUiAutomator(driver, expression)
        }
        searchField.sendKeys(text)
    }

    @When("^I tap on the \"Search\" button$")
    fun iTapOnSearchButton() {
        // Tap on the search button
//        searchField.sendKeys(Keys.ENTER)
    }

    @When("^I select \"(.*)\" from the search results$")
    fun iSelectFromSearchResults(text: String) {
       //Wait for the search results to load
        val searchResults = DriverManager.WaitUtils.waitUntilElementIsVisible(By.xpath("//h1[text()='Search results']"), 10)

        Assert.assertNotNull("Search results not found", searchResults)

        // Click on the desired search result
        val searchResult = driver.findElement(By.xpath("//li[@class='search-results__item']//h1[text()='$text']"))
        searchResult.click()
    }

    @When("^I tap on the \"More\" button$")
    fun iTapOnMoreButton() {
        // Tap on the More button
        driver.findElement(By.id("More")).click()
    }

    @When("^I select \"(.*)\" from the More Topics$")
    fun iSelectFromMoreTopics(text: String) {
        // Click on the desired topic
        val topic = DriverManager.WaitUtils.waitUntilElementIsVisible(By.xpath("//a[text()='$text']"), 300)
        Assert.assertNotNull("Topic not found", topic)
        topic.click()
    }

    @Then("^I should see the Navigation bar text \"(.*)\"$")
    fun iShouldSeeNavigationBarText(text: String) {
        // Wait for the navigation bar text to load
        val navigationBarText = DriverManager.WaitUtils.waitUntilElementIsVisible(By.xpath("//span[@class='nw-c-nav__wide-subnav__section-label']"), 300)
        Assert.assertNotNull("Navigation bar text not found", navigationBarText)
        Assert.assertEquals("Incorrect Navigation bar text", text, navigationBarText.text)
    }
}

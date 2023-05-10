package com.bbcnews.steps

//import com.bbcnews.utils.BaseTest
import com.bbcnews.utils.DriverManager
import io.appium.java_client.AppiumDriver
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.Assert
import org.junit.runner.RunWith
import org.openqa.selenium.By

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/features/verify_homepage_text.feature"],
    glue = ["com.bbcnews.steps"]
)
class VerifyHomePageTextSteps {

    private val driver: AppiumDriver = DriverManager.getDriver()

    @Given("^I am on a different page$")
    fun iAmOnADifferentPage() {
        // perform steps to navigate to a different page
    }

    @Then("^I should see the text \"([^\"]*)\" on the home page$")
    fun iShouldSeeTheText(expectedText: String) {
        val actualText = driver.findElement(By.xpath("//*[contains(@text, '$expectedText')]"))
        Assert.assertEquals(expectedText, actualText.text)
    }
}

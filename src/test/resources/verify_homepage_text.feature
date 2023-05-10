Feature: Verify Home Page Text

  Scenario: Navigate back to home page and verify the home page text
    Given I am on a different page
    When I tap on the "Home" button
    Then I should see the text "BBC News" on the home page
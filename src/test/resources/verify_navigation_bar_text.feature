Feature: Verify Navigation bar text after searching for England or selecting from More Topics

  @nav
  Scenario: Verify Navigation bar text after searching for England
    Given I am on the home page
    When I click on the Search icon
    And I enter "England" in the search field
    And I tap on the "Search" button
    And I select "England" from the search results
    Then I should see the Navigation bar text "England"

  Scenario: Verify Navigation bar text after selecting from More Topics
    Given I am on the home page
    When I tap on the "More" button
    And I select "England" from the More Topics
    Then I should see the Navigation bar text "England"
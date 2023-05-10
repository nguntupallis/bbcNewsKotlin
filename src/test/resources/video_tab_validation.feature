@video
Feature: Validate text on the Video tab

  Scenario: Validate text on the Video tab and scroll down to validate additional text
    Given I am on the home page
    When I tap on the "Video" tab
    Then I should see the text "BBC News Channel"
    When I scroll down
    Then I should see the text "Copyright © 2018 BBC"
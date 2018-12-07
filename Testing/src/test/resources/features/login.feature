@Login
Feature: Verify login functionality

  Scenario Outline: Validate the workflow for Login
    Given the user hits the URL
    And clicks on "Login"
    When I enter user credentials
      | username | <username> |
      | password | <password> |
    And clicks on "Submit"
    Then I verify user is logged in
    And I verify that main article has a picture or video
    When I click on main article
    Then I verify page is navigated to main article


    Examples:
      | TC_ID    | username     | password    |
      | Login_01 | digitaltest2 | Sphdigital1 |


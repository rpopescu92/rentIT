Feature: A user tries to search for some properties

  Scenario: A registered user searches for properties
    When A new user is registered
    And The user authenticates
    When The user creates some properties
    And The user searches for a property by title
    Then The user should receive the expected result
Feature: A user tries to search for some properties

  Scenario: A registered user searches for properties
    When A new user is registered
    And The user authenticates
    When The user creates only one properties
    And The user searches for that property by title
    Then The user should get only one property
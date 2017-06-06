Feature: A user tries to search for some properties

  Scenario: A registered user searches for one property
    When A new user is registered
    And The user authenticates
    When The user creates only one properties
    And The user searches for that property by title
    Then The user should get only one property


  Scenario: A registered user searches for multiple properties
    When A new user is registered
    And The user authenticates
    When The user creates 10 properties
    And The user searches for properties with price between 10 and 70
    Then The user should receive 3 properties

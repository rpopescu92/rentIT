Feature: Register functionality
  Scenario: A client tries to register
    When a new user is registered
    Then the status code should be 200
    And a user should be created
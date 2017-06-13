Feature: Login feature

  Scenario: A non existent user tries to log in
    When a non existent user tries to login then the status code should be 401

  Scenario: An existent user tries to log in
    When an existent user tries to login then the status code should be 200

  Scenario: An existent user tries to log in and get token
    When an user tries to login then get the authentication token
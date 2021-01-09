Feature: user admin

  Scenario: admin wants authentication
    When the client authentication with phone number is +77777777777 and password is testpassword
    Then the response's code is 200
    Then the response's body is empty
    Then the response has token

  Scenario: user admin gets users list
    When the admin gets users list
    Then the response's code is 200
    Then the response's body is not empty

  Scenario: user admin gets user by id
    When the admin gets user by eb8aefef-d441-4030-a3b1-ab99ea379b19
    Then the response's code is 200
    Then the response's body is not empty


  Scenario: admin wants logout
    When the client logout
    Then the response's code is 200
    Then the response's body is empty
    Then the response has not token

  Scenario: not authentication user admin gets users list
    When the admin gets users list
    Then the response's code is 403
    Then the response's body is not empty

  Scenario: not authentication user admin gets user by id
    When the admin gets user by eb8aefef-d441-4030-a3b1-ab99ea379b19
    Then the response's code is 403
    Then the response's body is not empty

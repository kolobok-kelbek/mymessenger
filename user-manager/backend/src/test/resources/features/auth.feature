Feature: Auth

  Scenario: admin wants authentication
    When the client authentication with phone number is +77777777777 and password is testpassword
    Then the response's code is 200
    Then the response's body is empty
    Then the response has token

  Scenario: admin wants logout
    When the client logout
    Then the response's code is 200
    Then the response's body is empty
    Then the response has not token

  Scenario: not exist user wants authentication with wrong parameters
    When the client authentication with phone number is +fdsafdsa and password is test
    Then the response's code is 403
    Then the response's body is empty
    Then the response has not token

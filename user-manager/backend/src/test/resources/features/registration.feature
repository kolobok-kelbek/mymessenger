Feature: Registration

  Scenario: client registration
    When the client registration with phone number is +71111111111 and password is testpassword
    Then the response's code is 200
    Then the response's body is not empty

  Scenario: new user wants authentication
    When the client authentication with phone number is +71111111111 and password is testpassword
    Then the response's code is 200
    Then the response's body is empty
    Then the response has token

  Scenario: client registration with wrong parameters
    When the client registration with phone number is +fdsafdsa and password is test
    Then the response's code is 400

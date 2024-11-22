Feature: Create a new client

  Scenario: Successfully create a client
    Given a client with CPF "12345678901", name "John Doe", and email "john.doe@example.com"
    When the client sends a POST request to "/api/clients"
    Then the response status should be 201
    And the response body should contain the CPF "12345678901", name "John Doe", and email "john.doe@example.com"

  Scenario: Fail to create a client with an existing CPF
    Given a client already exists with CPF "12345678901"
    When the client sends a POST request to "/api/clients"
    Then the response status should be 409
    And the response body should contain an error message "Client already exists"

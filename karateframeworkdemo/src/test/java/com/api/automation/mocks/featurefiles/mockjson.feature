Feature: To test the mock API created using WireMock standalone server
    
Background: Setup the Base path
* def baseUrl = wiremockurl

Scenario: Validate SUCCESS response

    Given url baseUrl + '/payment/status'
    When method GET
    Then status 200
    And match response.status == 'SUCCESS'
    And match response.message == 'Payment processed'

Scenario: Validate FAILURE response

    Given url baseUrl + '/payment/status?type=fail'
    When method GET
    Then status 500
    And match response.status == 'FAILED'
    And match response.message == 'Payment declined'
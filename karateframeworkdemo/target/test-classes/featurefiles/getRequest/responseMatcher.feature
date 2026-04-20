Feature: To validate the GET End point
  To validate the get end point response

  Background: Setup the base url
    * url baseUrl

  Scenario: To get the data in JSON format
    Given path '/normal/webapi/all'
    And header Accept = 'application/json'
    When method get
    Then status 200
    And print response
    And match response[0] ==
      """
      {
      "jobId": "#number",
      "jobTitle": "#string",
      "project": "#array",
      "jobDescription": "#string",
      "experience": "#array"
      }
      """

  Scenario: To get the data in xml format
    Given path '/normal/webapi/all'
    And header Accept = 'application/xml'
    When method get
    Then status 200
    And print response
    And match response/List/item[1] ==
      """
      <item>
      <jobId>#string</jobId>
      <jobTitle>#string</jobTitle>
      <jobDescription>#string</jobDescription>
      <experience>##</experience>
      <project>##</project>
      </item>
      """

  Scenario: To get the data in JSON format and validate using negate condition
    Given path '/normal/webapi/all'
    And header Accept = 'application/json'
    When method get
    Then status 200
    And print response
    And match response !=
      """
      [
      {
      "jobId": 2,
      "jobTitle": "Software Engg",
      "project": [
        {
          "technology": [
            "Kotlin",
            "SQL Lite",
            "Gradle"
          ],
          "projectName": "Movie App"
        }
      ],
      "jobDescription": "To develop andriod application",
      "experience": [
        "Google",
        "Apple",
        "Mobile Iron"
      ]
      }
      ]
      """

  Scenario: To get the data in JSON format and validate a specific property
    Given path '/normal/webapi/all'
    And header Accept = 'application/json'
    When method get
    Then status 200
    And print response
    And match response contains deep {"jobDescription": "To develop andriod application"}
    And match response contains deep {"project":[{"projectName": "Movie App"}]}
    And match header Content-Type == 'application/json'
    And match header Connection == 'keep-alive'
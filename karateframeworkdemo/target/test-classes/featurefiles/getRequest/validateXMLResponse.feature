Feature: To validate the GET End point
  To validate the get end point response

  Background: Setup the Base path and seed test entry
    * url baseUrl
    Given path '/normal/webapi/add'
    And request {"jobId": 1, "jobTitle": "Software Engg", "jobDescription": "To develop andriod application", "experience": ["Google", "Apple", "Mobile Iron"], "project": [{"projectName": "Movie App", "technology": ["Kotlin", "SQL Lite", "Gradle"]}]}
    And headers {Accept : 'application/json', Content-Type: 'application/json'}
    When method post
    Then status 201

  Scenario: To get the data in XML format
    Given path '/normal/webapi/all'
    And header Accept = 'application/xml'
    When method get
    Then status 200
    And match response/List/item[jobId='1' and jobTitle='Software Engg']/jobId == '1'
    And match response/List/item[jobId='1' and jobTitle='Software Engg']/jobTitle == 'Software Engg'
    And match response/List/item[jobId='1' and jobTitle='Software Engg']/experience/experience[1] == 'Google'
    And match response/List/item[jobId='1' and jobTitle='Software Engg']/project/project/projectName == 'Movie App'
    And match response/List/item[jobId='1' and jobTitle='Software Engg']/project/project/technology/technology[2] == 'SQL Lite'
    # Skip the response keyword
    And match /List/item[jobId='1' and jobTitle='Software Engg']/experience/experience[1] == 'Google'
    # Travers the xml similar to JSON
    And match response/List/item[jobId='1' and jobTitle='Software Engg']/experience/experience[1] == 'Google'

  Scenario: To get the data in XML format and validate using fuzzy matcher
    Given path '/normal/webapi/all'
    And header Accept = 'application/xml'
    When method get
    Then status 200
    And match response/List/item[jobId='1' and jobTitle='Software Engg']/jobId == '#notnull'
    And match response/List/item[jobId='1' and jobTitle='Software Engg']/jobTitle == '#string'
    And match response/List/item[jobId='1' and jobTitle='Software Engg']/experience/experience[1] == '#notnull'
    And match response/List/item[jobId='1' and jobTitle='Software Engg']/project/project/projectName == '#present'
    And match response/List/item[jobId='1' and jobTitle='Software Engg']/project/project/technology/technology[2] == '#ignore'
    And match response/List/item[jobId='1' and jobTitle='Software Engg']/jobTitle == '#string? _.length == 13'
    And match response/List/item[jobId='1' and jobTitle='Software Engg']/jobTitle.id == '#notpresent'
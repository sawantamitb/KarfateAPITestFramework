Feature: To validate the GET End point
  To validate the get end point response

  Background: Setup the base url and seed test entry
    * url baseUrl
    Given path '/normal/webapi/add'
    And request {"jobId": 1, "jobTitle": "Software Engg", "jobDescription": "To develop andriod application", "experience": ["Google", "Apple", "Mobile Iron"], "project": [{"projectName": "Movie App", "technology": ["Kotlin", "SQL Lite", "Gradle"]}]}
    And headers {Accept : 'application/json', Content-Type: 'application/json'}
    When method post
    Then status 201

  Scenario: To get the data in JSON format
    Given path '/normal/webapi/all'
    And header Accept = 'application/json'
    When method get
    Then status 200
    * def entry = karate.filter(response, function(x){ return x.jobId == 1 && x.jobTitle == 'Software Engg' })[0]
    And match entry.jobId == 1
    And match entry.experience[1] == 'Apple'
    And match entry.project[0].projectName == 'Movie App'
    And match entry.project[0].technology[2] == 'Gradle'
    #validate the size of array
    And match entry.experience == '#[3]'
    And match entry.project[0].technology == '#[3]'
    # Using wild card char
    And match entry.experience[*] == ['Google','Apple','Mobile Iron']
    And match entry.project[0].technology[*] == ['Kotlin','SQL Lite','Gradle']
    # Contains keyword
    And match entry.experience[*] contains ['Mobile Iron','Apple']
    And match entry.project[0].technology[*] contains ['SQL Lite']
    And match response.[*].jobId contains 1

  Scenario: To get the data in JSON format and validate using fuzzy matcher
    Given path '/normal/webapi/all'
    And header Accept = 'application/json'
    When method get
    Then status 200
    * def entry = karate.filter(response, function(x){ return x.jobId == 1 && x.jobTitle == 'Software Engg' })[0]
    And match entry.jobId == '#present'
    And match entry.experience[1] == '#notnull'
    And match entry.project[0].projectName == '#ignore'
    And match entry.project[0].technology == '#array'
    And match entry.jobTitle == '#string'
    And match entry.jobId == '#number'
    # Complex Fuzzy matcher
    And match entry.jobId == '#? _ >= 1'
    And match entry.jobTitle == '#string? _.length == 13'
    # To validate the array
    And match entry.experience == '#[]'
    And match entry.experience == '#[3]'
    # Make sure it is a array of string
    And match entry.experience == '#[3] #string'
    And match entry.experience == '#[3] #string? _.length >= 2'
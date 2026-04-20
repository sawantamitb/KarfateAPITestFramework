Feature: To validate the GET End response from file
  To validate the get end point response from extenal file

  Background: Setup the base url and seed test entry
    * url baseUrl
    Given path '/normal/webapi/add'
    And request {"jobId": 1, "jobTitle": "Software Engg", "jobDescription": "To develop andriod application", "experience": ["Google", "Apple", "Mobile Iron"], "project": [{"projectName": "Movie App", "technology": ["Kotlin", "SQL Lite", "Gradle"]}]}
    And headers {Accept : 'application/json', Content-Type: 'application/json'}
    When method post
    Then status 201

  Scenario: To get the data in JSON format and validate from file
    Given path '/normal/webapi/all'
    And header Accept = 'application/json'
    When method get
    Then status 200
    # Create a variable to store the data from external file
    * def actualResponse = read('classpath:datafiles/JsonResponse.json')
    And print "File ==> ", actualResponse
    * def filteredResponse = karate.filter(response, function(x){ return x.jobId == 1 && x.jobTitle == 'Software Engg' })
    And match filteredResponse[0] == actualResponse[0]

  Scenario: To get the data in xml format
    Given path '/normal/webapi/all'
    And header Accept = 'application/xml'
    When method get
    Then status 200
    # Create the variable to read the data from xml file
    * def actualResponse = read('classpath:datafiles/XmlResponse.xml')
    * def expectedItem = karate.xmlPath(actualResponse, '/List/item[1]')
    And print "Xml Response ==> ", actualResponse
    * def xmlEntry = karate.xmlPath(response, "(/List/item[jobId='1' and jobTitle='Software Engg'])[1]")
    And match xmlEntry == expectedItem
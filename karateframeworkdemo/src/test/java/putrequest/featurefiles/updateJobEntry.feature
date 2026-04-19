Feature: To test the updation of Job entry in the test application &  Test the end point PUT /normal/webapi/update

  Background: Create and Initialize base Url
    * url baseUrl

  Scenario: To update the Job Entry for exiting job in JSON format
    # Create a new Job Entry
    # Update the Job Entry using PUT request
    # Using jsonPath verify the updation of details in Job Entry
    Given path '/normal/webapi/add'
    * def getRandomValue = function() {return Math.floor((100) * Math.random());}
    * def id = getRandomValue()
    And request {  "jobId": '#(id)',"jobTitle": "Software Engg - 2", "jobDescription": "To develop andriod application", "experience": [ "Google", "Apple", "Mobile Iron", "Google" ], "project": [ { "projectName": "Movie App", "technology": [ "Kotlin", "SQL Lite","Gradle", "Jenkins" ] } ]}
    And headers {Accept : 'application/json', Content-Type: 'application/json'}
    When method post
    And status 201

    # PUT request
    Given path '/normal/webapi/update'
    And request
      """
      {
      "jobId":'#(id)',
      "jobTitle":"Software Engg - 3",
      "jobDescription":"To develop andriod application and Web Application",
      "experience":[
        "Google",
        "Apple",
        "Mobile Iron",
        "Google"
      ],
      "project":[
        {
           "projectName":"Movie App",
           "technology":[
              "Kotlin",
              "SQL Lite",
              "Gradle",
              "Jenkins"
           ]
        },
        {
           "projectName":"Movie App",
           "technology":[
              "Kotlin",
              "SQL Lite",
              "Gradle",
              "Jenkins"
           ]
        }
      ]
      }
      """
    And headers {Accept : 'application/json', Content-Type: 'application/json'}
    When method put
    Then status 200
    * def projectArray = karate.jsonPath(response, "$[?(@.jobId == " + id + ")].project")
    And print projectArray
    * assert projectArray[0].length == 2

  Scenario: To update the Job Entry for non-exiting job in JSON format
    # Create a new Job Entry
    # Update the Job Entry using PUT request
    # Using jsonPath verify the updation of details in Job Entry
    Given path '/normal/webapi/add'
    * def getRandomValue = function() {return Math.floor((10000) * Math.random());}
    * def id = getRandomValue()
    And request {  "jobId": '#(id)',"jobTitle": "Software Engg - 2", "jobDescription": "To develop andriod application", "experience": [ "Google", "Apple", "Mobile Iron", "Google" ], "project": [ { "projectName": "Movie App", "technology": [ "Kotlin", "SQL Lite","Gradle", "Jenkins" ] } ]}
    And headers {Accept : 'application/json', Content-Type: 'application/json'}
    When method post
    And status 201
    # PUT request
    Given path '/normal/webapi/update'
    And request
      """
      {
      "jobId":'#(getRandomValue())',
      "jobTitle":"Software Engg - 3",
      "jobDescription":"To develop andriod application and Web Application",
      "experience":[
        "Google",
        "Apple",
        "Mobile Iron",
        "Google"
      ],
      "project":[
        {
           "projectName":"Movie App",
           "technology":[
              "Kotlin",
              "SQL Lite",
              "Gradle",
              "Jenkins"
           ]
        },
        {
           "projectName":"Movie App",
           "technology":[
              "Kotlin",
              "SQL Lite",
              "Gradle",
              "Jenkins"
           ]
        }
      ]
      }
      """
    And headers {Accept : 'application/json', Content-Type: 'application/json'}
    When method put
    Then status 404

  Scenario: To update the Job Entry for exiting job in JSON format by calling another feature file
    # <Gherkin keyword> <call> <read(<location of file>)>
    Given call read("classpath:postRequest/featurefiles/createJobEntry.feature")
   
    # PUT request
    Given path '/normal/webapi/update'
    And request
      """
      {
      "jobId":5,
      "jobTitle":"Software Engg - 3",
      "jobDescription":"To develop andriod application and Web Application",
      "experience":[
        "Google",
        "Apple",
        "Mobile Iron",
        "Google"
      ],
      "project":[
        {
           "projectName":"Movie App",
           "technology":[
              "Kotlin",
              "SQL Lite",
              "Gradle",
              "Jenkins"
           ]
        },
        {
           "projectName":"Movie App",
           "technology":[
              "Kotlin",
              "SQL Lite",
              "Gradle",
              "Jenkins"
           ]
        }
      ]
      }
      """
    And headers {Accept : 'application/json', Content-Type: 'application/json'}
    When method put
    Then status 200
    * def projectArray = karate.jsonPath(response, "$[?(@.jobId == 5)].project")
    And print projectArray
    And match projectArray[0] == '#[2]'

  Scenario: To update the Job Entry for exiting job in JSON format by calling another feature file using shared context
    # <Gherkin keyword> <call> <read(<location of file>)>
    #Given call read("../createJobEntry.feature")
    * def postRequest = call read("classpath:postRequest/featurefiles/createJobEntry.feature")

     # Step 2: Extract ID
    * def jobId = postRequest.response.jobId

    And print "Calling Feature == > ", postRequest.response.jobId
    # And print "Calling Feature == > ", postRequest.getRandomValue()
    # PUT request
    Given path '/normal/webapi/update'
    And request
      """
      {
      "jobId":'#(jobId)',
      "jobTitle":"Software Engg - 3",
      "jobDescription":"To develop andriod application and Web Application",
      "experience":[
        "Google",
        "Apple",
        "Mobile Iron",
        "Google"
      ],
      "project":[
        {
           "projectName":"Movie App",
           "technology":[
              "Kotlin",
              "SQL Lite",
              "Gradle",
              "Jenkins"
           ]
        },
        {
           "projectName":"Movie App",
           "technology":[
              "Kotlin",
              "SQL Lite",
              "Gradle",
              "Jenkins"
           ]
        }
      ]
      }
      """
    And headers {Accept : 'application/json', Content-Type: 'application/json'}
    When method put
    Then status 200
    * def projectArray = karate.jsonPath(response, "$[?(@.jobId == " + jobId + ")].project")
    And print projectArray
    And match projectArray[0] == '#[2]'

  Scenario: To update the Job Entry for exiting job in JSON format by calling another feature file with variables
    # <Gherkin keyword> <call> <read(<location of file>)>
    #Given call read("../createJobEntry.feature") { var1:value, var2:value }
    * def getRandomValue = function() {return Math.floor((100) * Math.random());}
    * def id = getRandomValue()
    * def postRequest = call read("classpath:postRequest/featurefiles/createJobEntry.feature") { _url:'http://localhost:9897', _path:'/normal/webapi/add', _id:'#(id)' }
    And print "Calling Feature == > ", id
    #And print "Calling Feature == > ", postRequest
    # PUT request
    Given path '/normal/webapi/update'
    And request
      """
      {
      "jobId":'#(id)',
      "jobTitle":"Software Engg - 3",
      "jobDescription":"To develop andriod application and Web Application",
      "experience":[
        "Google",
        "Apple",
        "Mobile Iron",
        "Google"
      ],
      "project":[
        {
           "projectName":"Movie App",
           "technology":[
              "Kotlin",
              "SQL Lite",
              "Gradle",
              "Jenkins"
           ]
        },
        {
           "projectName":"Movie App",
           "technology":[
              "Kotlin",
              "SQL Lite",
              "Gradle",
              "Jenkins"
           ]
        }
      ]
      }
      """
    And headers {Accept : 'application/json', Content-Type: 'application/json'}
    When method put
    Then status 200
    * def projectArray = karate.jsonPath(response, "$[?(@.jobId == " + id + ")].project")
    And print projectArray
    And match projectArray[0] == '#[2]'
    
    
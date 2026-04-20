Feature: To upload the file using the Karate Framework

  Background: Setup the Base path
    * url baseUrl

  Scenario: To upload the in the test application
    Given path '/normal/webapi/upload'
    # location of file, name of the file , content-type header value
    And multipart file file = { read:'classpath:com/api/automation/datafiles/fileupload.txt', filename:'fileupload.txt', Content-type:'multipart/form-data'  }
    When method post
    Then status 200
    And print response

  Scenario: To upload the in the test application with json data
    Given path '/normal/webapi/upload'
    # location of file, name of the file , content-type header value
    * def fileLocation = 'classpath:com/api/automation/datafiles/jobEntry.json'
    And multipart file file = { read:'#(fileLocation)', filename:'jobEntry.json', Content-type:'multipart/form-data'  }
    When method post
    Then status 200
    And print response
    And match response.message contains 'jobEntry.json'
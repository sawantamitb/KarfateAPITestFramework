Feature: Karate JavaScript API Functions
  Demonstrates karate.map, karate.merge, karate.append, karate.match, karate.get, karate.set, karate.forEach

  Background: Setup the Base path
    * url baseUrl

  Scenario: karate.map - Transform each element of response array
    Given path '/normal/webapi/all'
    When method get
    Then status 200
    * def jobTitles = karate.map(response, function(x){ return x.jobTitle })
    * print 'All Job Titles:', jobTitles
    * def summaries = karate.map(response, function(x){ return { id: x.jobId, title: x.jobTitle } })
    * print 'Job Summaries:', summaries
    * match each summaries == { id: '#number', title: '#string' }

  Scenario: karate.forEach - Iterate over array for side-effects
    Given path '/normal/webapi/all'
    When method get
    Then status 200
    * karate.forEach(response, function(x, i){ karate.log(i + ' => ' + x.jobTitle) })

  Scenario: karate.merge - Deep merge two JSON objects
    * def baseRequest = { jobId: 100, jobTitle: 'Engineer' }
    * def extraFields = { jobDescription: 'Build APIs', experience: 5 }
    * def merged = karate.merge(baseRequest, extraFields)
    * print 'Merged:', merged
    * match merged == { jobId: 100, jobTitle: 'Engineer', jobDescription: 'Build APIs', experience: 5 }

  Scenario: karate.append - Concatenate arrays
    * def list1 = [1, 2, 3]
    * def result = karate.append(list1, 4, 5)
    * print 'Appended:', result
    * match result == [1, 2, 3, 4, 5]
    * def array1 = [{ id: 1 }]
    * def array2 = [{ id: 2 }, { id: 3 }]
    * def combined = karate.append(array1, array2)
    * print 'Combined:', combined
    * match combined == '#[3]'

  Scenario: karate.get and karate.set - Read and write Karate variables
    * def originalValue = 'Hello'
    * karate.set('dynamicVar', 'World')
    * def retrieved = karate.get('dynamicVar')
    * print 'Retrieved:', retrieved
    * match retrieved == 'World'
    * def missing = karate.get('nonExistentVar')
    * match missing == '#null'

  Scenario: karate.match - Programmatic assertions in JavaScript
    * def job = { jobId: 1, jobTitle: 'Software Engg', experience: 5 }
    * def result = karate.match(job, { jobId: '#number', jobTitle: '#string', experience: '#number' })
    * print 'Match result:', result
    * assert result.pass
    * def failResult = karate.match(job, { jobId: '#string' })
    * print 'Fail result:', failResult
    * assert !failResult.pass

  Scenario: Combining map and filter on API response
    Given path '/normal/webapi/all'
    When method get
    Then status 200
    * def filtered = karate.filter(response, function(x){ return x.jobId > 0 })
    * def titles = karate.map(filtered, function(x){ return x.jobTitle })
    * print 'Filtered titles:', titles
    * match each titles == '#string'

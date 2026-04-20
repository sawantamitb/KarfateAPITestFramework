package com.api.automation.getRequest.runner;


import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestGetRunner {
	
	@Test
	public Karate runTest() {
		return Karate.run(
				"classpath:com/api/automation/getRequest/featurefiles/getRequest.feature",
				"classpath:com/api/automation/getRequest/featurefiles/responseMatcher.feature",
				"classpath:com/api/automation/getRequest/featurefiles/validateJSONArray.feature",
				"classpath:com/api/automation/getRequest/featurefiles/validateXMLResponse.feature",
				"classpath:com/api/automation/getRequest/featurefiles/Variables.feature",
				"classpath:com/api/automation/getRequest/featurefiles/ValidationUsingFile.feature"
				)
				.tags("~@ignore");
	}
	
	/* @Test
	public Karate runTestUsingClassPath() {
		return Karate.run("classpath:com/api/automation/getrequest/getRequest.feature");
	} */
}
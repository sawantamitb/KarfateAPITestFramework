package com.api.automation.runner.getRequest;


import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestGetRunner {
	
	@Test
	public Karate runTest() {
		return Karate.run(
				"classpath:featurefiles/getRequest/getRequest.feature",
				"classpath:featurefiles/getRequest/responseMatcher.feature",
				"classpath:featurefiles/getRequest/validateJSONArray.feature",
				"classpath:featurefiles/getRequest/validateXMLResponse.feature",
				"classpath:featurefiles/getRequest/Variables.feature",
				"classpath:featurefiles/getRequest/ValidationUsingFile.feature"
				)
				.tags("~@ignore");
	}
	
	/* @Test
	public Karate runTestUsingClassPath() {
		return Karate.run("classpath:com/api/automation/getrequest/getRequest.feature");
	} */
}
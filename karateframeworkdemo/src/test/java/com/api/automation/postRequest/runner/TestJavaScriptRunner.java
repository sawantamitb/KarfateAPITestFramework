package com.api.automation.postRequest.runner;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestJavaScriptRunner{
	
	@Test
	public Karate runTest() {
		return Karate.run(
			"classpath:com/api/automation/postRequest/featurefiles/javaScriptExecutor.feature"		
		)
		.tags("~@ignore");
	}	
}

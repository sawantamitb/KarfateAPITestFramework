package com.api.automation.runner.postRequest;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestJavaScriptRunner{
	
	@Test
	public Karate runTest() {
		return Karate.run(
			"classpath:featurefiles/postRequest/javaScriptExecutor.feature",
			"classpath:featurefiles/postRequest/karateJavaScriptAPI.feature"
		)
		.tags("~@ignore");
	}	
}

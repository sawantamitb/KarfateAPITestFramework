package com.api.automation.runner.postRequest;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestPostRunner{
	
	@Test
	public Karate runTest() {
		return Karate.run(
			"classpath:featurefiles/postRequest/createJobEntry.feature",
			"classpath:featurefiles/postRequest/schemaValidation.feature"
			)
		.tags("~@ignore");
	}	
}

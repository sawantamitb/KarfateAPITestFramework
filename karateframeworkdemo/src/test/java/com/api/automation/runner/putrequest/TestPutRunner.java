package com.api.automation.runner.putrequest;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestPutRunner {
	
	@Test
	public Karate runTest() {
		return Karate.run("classpath:featurefiles/putrequest/updateJobEntry.feature").tags("~@ignore");
	}
	
	
}
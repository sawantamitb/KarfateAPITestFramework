package com.api.automation.putrequest.runner;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestPutRunner {
	
	@Test
	public Karate runTest() {
		return Karate.run("classpath:com/api/automation/putrequest/featurefiles/updateJobEntry.feature").relativeTo(getClass());
	}
	
	
}
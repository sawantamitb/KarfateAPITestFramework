package com.api.automation.datadriven.runner;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestDataDrivenRunner {
	
	@Test
	public Karate runTest() {
		return Karate.run("classpath:com/api/automation/datadriven/featurefiles/postDataDriven.feature").relativeTo(getClass());
	}
	
	
}
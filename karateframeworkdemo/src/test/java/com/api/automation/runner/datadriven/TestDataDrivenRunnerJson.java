package com.api.automation.datadriven.runner;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestDataDrivenRunnerJson {
	
	@Test
	public Karate runTest() {
		return Karate.run("classpath:featurefiles/datadriven/postDataDrivenJson.feature").tags("~@ignore");
	}
	
	
}
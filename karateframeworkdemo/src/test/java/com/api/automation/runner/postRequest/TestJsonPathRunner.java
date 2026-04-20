package com.api.automation.runner.postRequest;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestJsonPathRunner
{
	
	@Test
	public Karate runTest() 
	{
		return Karate.run(
			"classpath:featurefiles/postRequest/jsonPathExpression.feature"		
		)
		.tags("~@ignore");
	}	
}

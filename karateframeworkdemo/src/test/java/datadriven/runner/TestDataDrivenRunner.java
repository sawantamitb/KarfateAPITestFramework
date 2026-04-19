package datadriven.runner;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestDataDrivenRunner {
	
	@Test
	public Karate runTest() {
		return Karate.run("classpath:datadriven/featurefiles/postDataDriven.feature").relativeTo(getClass());
	}
	
	
}
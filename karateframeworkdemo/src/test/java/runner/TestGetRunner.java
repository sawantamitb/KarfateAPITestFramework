package runner;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestGetRunner {
	
	@Test
	public Karate runTest() {
		return Karate.run(
				"classpath:getRequest/getRequest.feature",
				"classpath:getRequest/responseMatcher.feature",
				"classpath:getRequest/validateJSONArray.feature","classpath:getRequest/validateXMLResponse.feature")
				.tags("~@ignore");
	}
	
	/* @Test
	public Karate runTestUsingClassPath() {
		return Karate.run("classpath:com/api/automation/getrequest/getRequest.feature");
	} */
}
package getRequest.runner;


import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestGetRunner {
	
	@Test
	public Karate runTest() {
		return Karate.run(
				"classpath:getRequest/featurefiles/getRequest.feature",
				"classpath:getRequest/featurefiles/responseMatcher.feature",
				"classpath:getRequest/featurefiles/validateJSONArray.feature",
				"classpath:getRequest/featurefiles/validateXMLResponse.feature",
				"classpath:getRequest/featurefiles/Variables.feature",
				"classpath:getRequest/featurefiles/ValidationUsingFile.feature"
				)
				.tags("~@ignore");
	}
	
	/* @Test
	public Karate runTestUsingClassPath() {
		return Karate.run("classpath:com/api/automation/getrequest/getRequest.feature");
	} */
}
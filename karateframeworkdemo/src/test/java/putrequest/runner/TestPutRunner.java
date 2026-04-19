package putrequest.runner;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestPutRunner {
	
	@Test
	public Karate runTest() {
		return Karate.run("classpath:putrequest/featurefiles/updateJobEntry.feature").relativeTo(getClass());
	}
	
	
}
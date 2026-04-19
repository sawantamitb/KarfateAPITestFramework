package fileupload.runner;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestFileUploadRunner{
	
	@Test
	public Karate runTest() {
		return Karate.run(
			"classpath:fileupload/featurefiles/fileUpload.feature"		
		)
		.tags("~@ignore");
	}	
}

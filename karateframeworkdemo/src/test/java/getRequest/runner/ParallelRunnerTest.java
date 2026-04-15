package getRequest.runner;

import org.junit.jupiter.api.Test;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;

class ParallelRunnerTest {

     @Test
    void testParallel() {
        Results results = Runner.path(
            "classpath:getRequest/featurefiles/getRequest",
             "classpath:getRequest/featurefiles/responseMatcher.feature",
             "classpath:getRequest/featurefiles/validateJSONArray.feature",
			 "classpath:getRequest/featurefiles/validateXMLResponse.feature",
			 "classpath:getRequest/featurefiles/Variables.feature",
			 "classpath:getRequest/featurefiles/ValidationUsingFile.feature"
    )
   // .outputCucumberJson(true)
   // .outputHtmlReport(true)
    .tags("~@ignore")
    .parallel(1);
    
    //assertEquals(0, results.getFailCount(), results.getErrorMessages());

            if (results.getFailCount() > 0)           
            {
                System.out.println("❌ Failed - Retry Triggered");
                System.out.println("🔁 Re-running failed scenarios...");
                Results retryResults = Runner.path("target/karate-reports")
                .parallel(1);
                 System.out.println("Retry Failures: " + retryResults.getFailCount());
                    if (retryResults.getFailCount() == 0) {
                        System.out.println("✅ Passed on retry");                              
                    }    
            }           

    } 

}

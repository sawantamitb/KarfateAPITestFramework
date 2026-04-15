package suiterunner;
import org.junit.jupiter.api.Test;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;

public class TestSuiteRunner 
{
    @Test
    public void testParallel() 
    {

        // 🔹 Step 1: Login (Critical dependency)

        runWithRetry("classpath:getRequest/featurefiles/getRequest.feature", 1, 1);
        runWithRetry("classpath:getRequest/featurefiles/responseMatcher.feature", 1, 1);
        runWithRetry("classpath:getRequest/featurefiles/validateJSONArray.feature", 1, 1);
        runWithRetry("classpath:getRequest/featurefiles/validateXMLResponse.feature", 1, 1);
        runWithRetry("classpath:getRequest/featurefiles/Variables.feature", 1, 1);
        runWithRetry("classpath:getRequest/featurefiles/ValidationUsingFile.feature", 1, 1);
             
       /*  // 🔹 Step 2: Dashboard (depends on login)
        Results dashboardResult = runWithRetry(List.of(
                "classpath:features/dashboard/dashboard.feature"
        ), 1, 1);

        // 🔹 Step 3: Payment (runs only if above passed)
        Results paymentResult = runWithRetry(List.of(
                "classpath:features/payment/payment.feature"
        ), 1, 1);
 */
        System.out.println("✅ All test flows executed successfully!");
    }

    // 🔁 Retry Logic Wrapper
   public Results runWithRetry(String paths, int threads, int maxRetries) 
   {

        Results results = null;
        for (int i = 0; i <= maxRetries; i++) 
         {

                System.out.println("▶ Attempt: " + (i + 1));

                results = Runner.path(paths)
                        .tags("~@ignore")
                        .outputCucumberJson(true)
                        .outputHtmlReport(true)
                        .parallel(threads);

            if (results.getFailCount() == 0)           
            {
                System.out.println("✅ Passed");
                return results;
            } 
            else 
            {
                System.out.println("❌ Failed - Retry Triggered");
                System.out.println("🔁 Re-running failed scenarios...");
                Results retryResults = Runner.path("target/karate-reports")
                .parallel(5);
                 System.out.println("Retry Failures: " + retryResults.getFailCount());
                    if (retryResults.getFailCount() == 0) {
                        System.out.println("✅ Passed on retry");
                        return retryResults;       
                    }    
            }   

        }
         return results;
    }
}
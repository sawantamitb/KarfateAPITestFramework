package suiterunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;

class ParallelRunnerTest {

    private static final List<String> FEATURES = List.of(
        "classpath:getRequest/featurefiles/getRequest",
        "classpath:getRequest/featurefiles/responseMatcher.feature",
        "classpath:getRequest/featurefiles/validateJSONArray.feature",
        "classpath:getRequest/featurefiles/validateXMLResponse.feature",
        "classpath:getRequest/featurefiles/Variables.feature",
        "classpath:getRequest/featurefiles/ValidationUsingFile.feature",
        "classpath:postRequest/featurefiles/javaScriptExecutor.feature",
        "classpath:postRequest/featurefiles/jsonPathExpression.feature",
        "classpath:postRequest/featurefiles/createJobEntry.feature",
        "classpath:postRequest/featurefiles/schemaValidation.feature"
    );

    @Test
    void testParallel() {
        Results results = runSuite(null, 1);

        if (results.getFailCount() == 0) {
            assertEquals(0, results.getFailCount(), results.getErrorMessages());
            return;
        }

        System.out.println("Failed - Retry Triggered");
        System.out.println("Re-running same suite...");

        Results retryResults = runSuite("target/karate-reports-retry", 1);
        System.out.println("Retry Failures: " + retryResults.getFailCount());
        assertEquals(0, retryResults.getFailCount(), retryResults.getErrorMessages());
    }

    private Results runSuite(String reportDirectory,int threadcount) {
        Runner.Builder<?> builder = Runner.path(FEATURES)
            .tags("~@ignore")
            .backupReportDir(false);

        if (reportDirectory != null) {
            builder.reportDir(reportDirectory);
        }

        return builder.parallel(threadcount);
    }

}

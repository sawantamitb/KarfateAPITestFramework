package getRequest.runner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;

class ParallelRunnerTest {

    @Test
    void testParallel() {
        Results results = Runner.path("classpath:featurefiles/getRequest")
                //.outputCucumberJson(true)
                .parallel(1);
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

}

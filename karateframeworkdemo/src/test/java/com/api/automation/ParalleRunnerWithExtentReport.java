package com.api.automation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.automation.extentreport.CustomExtentReport;
import com.intuit.karate.Results;
import com.intuit.karate.Runner.Builder;

import com.intuit.karate.junit5.Karate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import com.api.automation.utils.*;

public class ParalleRunnerWithExtentReport {

	private static final Logger logger = LoggerFactory.getLogger(ParalleRunnerWithExtentReport.class);

    @BeforeAll
    public static void setup() {
        WireMockManager.start();
    }

    @AfterAll
    public static void teardown() {
        WireMockManager.stop();
    }


	@Test
	public void executeKarateTest() {
		logger.info("=== Starting Karate Test Suite (First Run) ===");

		Results result = runSuite(null, 2);
		logger.debug("First run completed - Total: {},Total: {}, Passed: {}, Failed: {}",
				result.getScenariosTotal(), result.getScenariosPassed(), result.getScenariosFailed());
		
		/* System.out.println("Total Features   => " + result.getFeaturesTotal());
		System.out.println("Total Scenarios  => " + result.getScenariosTotal());
		System.out.println("Passed Scenarios => " + result.getScenariosPassed());
		System.out.println("Failed Scenarios => " + result.getScenariosFailed()); */
		
		generateExtentReport(result, "Karate Test Execution Report", Collections.emptySet());
		logger.debug("Extent report generated for first run at: {}", result.getReportDir());

		if (result.getFailCount() == 0) {
			logger.info("=== All scenarios passed. No retry needed. ===");
			assertEquals(0, result.getFailCount(), result.getErrorMessages());
			return;
		}

		Set<String> firstRunFailedIds = getFailedScenarioIds(result);
		logger.warn("First run failed with {} failure(s). Retry triggered.", result.getFailCount());
		logger.info("=== Re-running same suite (Retry Run) ===");

		Results retryResult = runSuite("target/karate-reports-retry", 2);
		logger.debug("Retry run completed - Total: {}, Passed: {}, Failed: {}",
				retryResult.getScenariosTotal(), retryResult.getScenariosPassed(), retryResult.getScenariosFailed());

		Set<String> flakyIds = getFlakyScenarioIds(firstRunFailedIds, retryResult);
		logger.info("Flaky tests: {} scenario(s) failed on first run but passed on retry", flakyIds.size());

		generateExtentReport(retryResult, "Karate Test Execution Report - Retry", flakyIds);
		logger.debug("Extent report generated for retry run at: {}", retryResult.getReportDir());

		if (retryResult.getFailCount() > 0) {
			logger.error("Retry also failed with {} failure(s). Error: {}",
					retryResult.getFailCount(), retryResult.getErrorMessages());
		} else {
			logger.info("=== Retry run passed successfully. ===");
		}

		assertEquals(0, retryResult.getFailCount(), retryResult.getErrorMessages());
	}

	private Results runSuite(String reportDirectory, int threadCount) {
		logger.debug("Configuring test suite - reportDir: {}, threads: {}",
				reportDirectory != null ? reportDirectory : "default", threadCount);
        
		String tags = System.getProperty("karate.tags", "~@ignore");
		Builder aRunner = new Builder();
		aRunner.path("classpath:featurefiles");
		aRunner.tags(tags.split("\\|"));
		aRunner.backupReportDir(false);
		if (reportDirectory != null) {
			aRunner.reportDir(reportDirectory);
			logger.debug("Custom report directory set to: {}", reportDirectory);
		}

		logger.info("Executing suite with {} thread(s)", threadCount);
		return aRunner.parallel(threadCount);
	}

	private void generateExtentReport(Results result, String reportTitle, Set<String> flakyScenarios) {
		logger.info("Generating Extent Report - Title: '{}', ReportDir: '{}'", reportTitle, result.getReportDir());

		CustomExtentReport extentReport = new CustomExtentReport()
				.withKarateResult(result)
				.withReportDir(result.getReportDir())
				.withReportTitle(reportTitle)
				.withFlakyScenarios(flakyScenarios);
		extentReport.generateExtentReport();

		logger.info("Extent Report generation completed successfully.");
	}

	private Set<String> getFailedScenarioIds(Results results) {
		return results.getScenarioResults()
				.filter(sr -> sr.isFailed())
				.map(sr -> sr.getScenario().getFeature().getName() + "::" + sr.getScenario().getName())
				.collect(Collectors.toSet());
	}

	private Set<String> getFlakyScenarioIds(Set<String> firstRunFailedIds, Results retryResults) {
		return retryResults.getScenarioResults()
				.filter(sr -> !sr.isFailed())
				.map(sr -> sr.getScenario().getFeature().getName() + "::" + sr.getScenario().getName())
				.filter(firstRunFailedIds::contains)
				.collect(Collectors.toSet());
	}
}

package com.api.automation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.automation.extentreport.CustomExtentReport;
import com.intuit.karate.Results;
import com.intuit.karate.Runner.Builder;

public class ParalleRunnerWithExtentReport {

	private static final Logger logger = LoggerFactory.getLogger(ParalleRunnerWithExtentReport.class);

	@Test
	public void executeKarateTest() {
		logger.info("=== Starting Karate Test Suite (First Run) ===");

		Results result = runSuite(null, 1);
		logger.debug("First run completed - Total: {}, Passed: {}, Failed: {}",
				result.getScenariosTotal(), result.getScenariosPassed(), result.getScenariosFailed());

		generateExtentReport(result, "Karate Test Execution Report");
		logger.debug("Extent report generated for first run at: {}", result.getReportDir());

		if (result.getFailCount() == 0) {
			logger.info("=== All scenarios passed. No retry needed. ===");
			assertEquals(0, result.getFailCount(), result.getErrorMessages());
			return;
		}

		logger.warn("First run failed with {} failure(s). Retry triggered.", result.getFailCount());
		logger.info("=== Re-running same suite (Retry Run) ===");

		Results retryResult = runSuite("target/karate-reports-retry", 1);
		logger.debug("Retry run completed - Total: {}, Passed: {}, Failed: {}",
				retryResult.getScenariosTotal(), retryResult.getScenariosPassed(), retryResult.getScenariosFailed());

		generateExtentReport(retryResult, "Karate Test Execution Report - Retry");
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

		Builder aRunner = new Builder();
		aRunner.path("classpath:com/api/automation");
		aRunner.tags("~@ignore");		
		aRunner.backupReportDir(false);
		if (reportDirectory != null) {
			aRunner.reportDir(reportDirectory);
			logger.debug("Custom report directory set to: {}", reportDirectory);
		}

		logger.info("Executing suite with {} thread(s)", threadCount);
		return aRunner.parallel(threadCount);
	}

	private void generateExtentReport(Results result, String reportTitle) {
		logger.info("Generating Extent Report - Title: '{}', ReportDir: '{}'", reportTitle, result.getReportDir());

		CustomExtentReport extentReport = new CustomExtentReport()
				.withKarateResult(result)
				.withReportDir(result.getReportDir())
				.withReportTitle(reportTitle);
		extentReport.generateExtentReport();

		logger.info("Extent Report generation completed successfully.");
	}
}

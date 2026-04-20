package com.api.automation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.karate.Results;
import com.intuit.karate.Runner.Builder;
import com.intuit.karate.core.Result;
import com.intuit.karate.core.ScenarioResult;
import com.intuit.karate.core.Step;
import com.intuit.karate.junit5.Karate;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParallelBuilderWithCucumberReport {

	@Test
	public void executeKarateTest() throws Exception {
		String tags = System.getProperty("karate.tags", "~@ignore");
		Builder aRunner = new Builder();
		aRunner.path("classpath:com/api/automation");
		aRunner.tags(tags.split("\\|"));
		Results result = aRunner.parallel(1);

		System.out.println("Total Features   => " + result.getFeaturesTotal());
		System.out.println("Total Scenarios  => " + result.getScenariosTotal());
		System.out.println("Passed Scenarios => " + result.getScenariosPassed());
		System.out.println("Failed Scenarios => " + result.getScenariosFailed());

		generateCucumberReport(result);
		Assertions.assertEquals(0, result.getFailCount(), "There are Some Failed Scenarios");
	}

	private void generateCucumberReport(Results results) throws Exception {
		// Collect named scenario results grouped by feature
		List<ScenarioResult> allScenarios = results.getScenarioResults()
				.filter(sr -> sr.getScenario().getName() != null && !sr.getScenario().getName().isEmpty())
				.collect(Collectors.toList());

		Map<String, List<ScenarioResult>> byFeature = new LinkedHashMap<>();
		Map<String, String> featureDescriptions = new LinkedHashMap<>();
		for (ScenarioResult sr : allScenarios) {
			String featureName = sr.getScenario().getFeature().getName();
			byFeature.computeIfAbsent(featureName, k -> new ArrayList<>()).add(sr);
			String desc = sr.getScenario().getFeature().getDescription();
			featureDescriptions.putIfAbsent(featureName, desc != null ? desc : "");
		}

		// Build Cucumber-compatible JSON structure
		List<Map<String, Object>> cucumberFeatures = new ArrayList<>();
		byFeature.forEach((featureName, scenarioResults) -> {
			Map<String, Object> featureMap = new LinkedHashMap<>();
			featureMap.put("id", featureName.toLowerCase().replaceAll("[^a-z0-9]+", "-"));
			featureMap.put("name", featureName);
			featureMap.put("description", featureDescriptions.get(featureName));
			featureMap.put("keyword", "Feature");
			featureMap.put("uri", featureName);
			featureMap.put("line", 1);

			List<Map<String, Object>> elements = new ArrayList<>();
			scenarioResults.forEach(sr -> {
				Map<String, Object> elementMap = new LinkedHashMap<>();
				String scenarioName = sr.getScenario().getName();
				elementMap.put("id", featureName.toLowerCase().replaceAll("[^a-z0-9]+", "-")
						+ ";" + scenarioName.toLowerCase().replaceAll("[^a-z0-9]+", "-"));
				elementMap.put("name", scenarioName);
				elementMap.put("keyword", "Scenario");
				elementMap.put("type", "scenario");
				elementMap.put("description", "");
				elementMap.put("line", 1);

				List<Map<String, Object>> steps = new ArrayList<>();
				sr.getStepResults().forEach(stepResult -> {
					Step step = stepResult.getStep();
					Result res = stepResult.getResult();

					Map<String, Object> stepMap = new LinkedHashMap<>();
					stepMap.put("keyword", step.getPrefix() + " ");
					stepMap.put("name", step.getText());
					stepMap.put("line", step.getLine());

					Map<String, Object> resultMap = new LinkedHashMap<>();
					resultMap.put("status", res.getStatus());
					resultMap.put("duration", res.getDurationNanos());
					if (res.getError() != null) {
						resultMap.put("error_message", res.getError().getMessage());
					}
					stepMap.put("result", resultMap);
					steps.add(stepMap);
				});

				elementMap.put("steps", steps);
				elements.add(elementMap);
			});

			featureMap.put("elements", elements);
			cucumberFeatures.add(featureMap);
		});

		// Write Cucumber JSON to a temp file
		File cucumberJsonDir = new File("target/cucumber-json");
		cucumberJsonDir.mkdirs();
		File cucumberJsonFile = new File(cucumberJsonDir, "karate-cucumber-results.json");
		new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(cucumberJsonFile, cucumberFeatures);

		// Generate Masterthought Cucumber HTML report
		File reportOutputDir = new File("target/cucumber-html-reports");
		Configuration configuration = new Configuration(reportOutputDir, "Karate API Tests");
		ReportBuilder reportBuilder = new ReportBuilder(
				Collections.singletonList(cucumberJsonFile.getAbsolutePath()), configuration);
		reportBuilder.generateReports();

		System.out.println("Cucumber HTML report: "
				+ reportOutputDir.getAbsolutePath() + File.separator + "overview-features.html");
	}

}
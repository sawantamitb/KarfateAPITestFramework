# Karate API Test Framework

A comprehensive API test automation framework built with [Karate](https://github.com/karatelabs/karate) and JUnit 5, demonstrating REST API testing with BDD-style feature files.

## Tech Stack

| Tool | Version |
|------|---------|
| Java | 21 |
| Karate | 1.4.1 |
| Maven | 3.x |
| JUnit | 5 |
| ExtentReports | 5.1.2 |
| WireMock | 3.13.2 |
| Cucumber Reporting | 5.11.0 |

## Project Structure

```
karateframeworkdemo/
├── pom.xml
└── src/test/
    ├── java/com/api/automation/
    │   ├── karate-config.js                          # Global config (env-based)
    │   ├── ParalleRunnerWithExtentReport.java        # Parallel runner + Extent Reports
    │   ├── ParallelBuilderWithCucumberReport.java    # Cucumber report builder
    │   ├── runner/
    │   │   ├── getRequest/TestGetRunner.java
    │   │   ├── postRequest/TestPostRunner.java
    │   │   ├── putrequest/TestPutRunner.java
    │   │   ├── fileupload/TestFileUploadRunner.java
    │   │   ├── datadriven/TestDataDrivenRunner.java
    │   │   └── mocks/WireMockRunner.java
    │   ├── extentreport/
    │   │   ├── CustomExtentReport.java
    │   │   └── logger/MaskLogger.java
    │   └── utils/
    │       ├── ConfigReader.java
    │       └── WireMockManager.java
    └── rersources/
        ├── config/
        │   ├── qa.json
        │   ├── stage.json
        │   └── prod.json
        ├── datafiles/
        │   ├── testData.csv
        │   ├── jobEntry.json
        │   ├── jobEntry.xml
        │   └── wiremock/
        └── featurefiles/
            ├── getRequest/       # GET endpoint validations
            ├── postRequest/      # POST with JSON/XML payloads
            ├── putrequest/       # PUT update operations
            ├── fileupload/       # File upload tests
            ├── datadriven/       # CSV & JSON data-driven tests
            └── mocks/            # WireMock stub tests
```

## Prerequisites

- **Java 21** or higher
- **Maven 3.x**
- A running instance of the target API on `localhost:9897` (or configure in environment JSON files)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/sawantamitb/KarfateAPITestFramework.git
cd KarfateAPITestFramework/karateframeworkdemo
```

### 2. Run all tests (default: QA environment)

```bash
mvn clean test
```

### 3. Run with a specific environment profile

```bash
# QA (default)
mvn clean test -Pqa

# Staging
mvn clean test -Pstage

# Production
mvn clean test -Pprod
```

### 4. Run the parallel suite with Extent Reports

```bash
mvn clean test -Pqa -Dtest=ParalleRunnerWithExtentReport
```

### 5. Run a specific test runner

```bash
mvn clean test -Dtest=TestGetRunner
mvn clean test -Dtest=TestPostRunner
mvn clean test -Dtest=TestPutRunner
mvn clean test -Dtest=TestDataDrivenRunner
```

### 6. Run tests by tag

```bash
mvn clean test -Dtest=ParalleRunnerWithExtentReport -Dkarate.tags=@sharedcontext
```

## Test Categories

| Category | Feature Files | Description |
|----------|--------------|-------------|
| **GET Requests** | 6 | Response validation, JSON/XML, matchers, variables |
| **POST Requests** | 4 | Job creation, schema validation, JSONPath, JS execution |
| **PUT Requests** | 1 | Update job entries |
| **File Upload** | 1 | Multipart file upload |
| **Data-Driven** | 2 | CSV and JSON parameterized tests |
| **Mocks** | 1 | WireMock API stubs |

## Key Features

- **Multi-environment support** — Switch between QA, Stage, and Prod via Maven profiles. Environment config is loaded dynamically from JSON files through `karate-config.js`.
- **Parallel execution** — Tests run in parallel (2 threads by default) for faster feedback.
- **Automatic retry with flaky test detection** — Failed scenarios are re-run automatically; tests that pass on retry are flagged as flaky in the report.
- **Extent Reports** — Rich HTML reports generated after each run in `target/karate-reports/`.
- **WireMock integration** — Mock server starts/stops automatically via `@BeforeAll`/`@AfterAll` lifecycle hooks.
- **JSON & XML support** — Request/response handling for both formats with schema validation.
- **Data-driven testing** — Parameterized tests using external CSV and JSON data files.
- **Embedded expressions** — Dynamic data generation using JavaScript functions within feature files.
- **Reusable scenarios** — Shared helper scenarios callable across feature files.

## Reports

After test execution, reports are available at:

| Report | Location |
|--------|----------|
| Karate HTML Report | `target/karate-reports/karate-summary.html` |
| Extent Report | `target/karate-reports/` |
| Retry Report | `target/karate-reports-retry/` |
| Surefire Report | `target/surefire-reports/` |

## Environment Configuration

Each environment has a JSON config file under `src/test/rersources/config/`:

```json
{
  "baseUrl": "http://localhost:9897",
  "username": "qaadmin",
  "password": "qawelcome",
  "wiremockport": "9898",
  "wiremockurl": "http://localhost:9898"
}
```

The active environment is selected via the Maven profile (`-Pqa`, `-Pstage`, `-Pprod`) and resolved in `karate-config.js`.

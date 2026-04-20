package com.api.automation.runner.mocks;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import com.api.automation.utils.*;

class WireMockRunner {

    @BeforeAll
    static void setup() {
        WireMockManager.start();
    }

    @AfterAll
    static void teardown() {
        WireMockManager.stop();
    }

    @Test
	public Karate runTest() {
		return Karate.run(
			"classpath:featurefiles/mocks/mockjson.feature"
			)
		.tags("~@ignore");
	}	
}
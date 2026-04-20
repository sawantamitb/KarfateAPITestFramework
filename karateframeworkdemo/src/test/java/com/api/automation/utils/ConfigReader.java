package com.api.automation.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;

public class ConfigReader {

    private final JsonNode config;

    /**
     * Loads config for the environment resolved from the
     * {@code karate.env} system property (defaults to "qa").
     */
    public ConfigReader() {
        this(System.getProperty("karate.env", "qa"));
    }

    /**
     * Loads config for the given environment name.
     * Falls back to qa.json when the requested file is not found.
     */
    public ConfigReader(String env) {
        JsonNode loaded = loadConfig(env);
        if (loaded == null && !"qa".equals(env)) {
            System.out.println("[ConfigReader] Config not found for env '" + env + "', falling back to 'qa'");
            loaded = loadConfig("qa");
        }
        this.config = loaded;
    }

    private JsonNode loadConfig(String env) {
        String path = "config/" + env + ".json";
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(path)) {
            if (is != null) {
                return new ObjectMapper().readTree(is);
            }
        } catch (Exception e) {
            System.out.println("[ConfigReader] Failed to load '" + path + "': " + e.getMessage());
        }
        return null;
    }

    /**
     * Returns the string value for the given key, or {@code null} if absent.
     */
    public String get(String key) {
        if (config != null && config.has(key)) {
            return config.get(key).asText();
        }
        return null;
    }

    /**
     * Returns the string value for the given key, or {@code defaultValue} if absent.
     */
    public String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Returns the integer value for the given key, or {@code defaultValue} if absent/invalid.
     */
    public int getInt(String key, int defaultValue) {
        String value = get(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("[ConfigReader] Non-integer value for key '" + key + "': " + value);
            }
        }
        return defaultValue;
    }
}

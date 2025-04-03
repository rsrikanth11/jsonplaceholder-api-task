package com.api.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Class to load configuration values from a properties file.
 */
public class Config {

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    /**
     * Get a configuration value by key.
     * 
     * @param key The key of the configuration.
     * @return The configuration value.
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }
}

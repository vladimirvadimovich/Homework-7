package configs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;

import java.io.IOException;
import java.io.InputStream;

public class ConfigLoader {

    @JsonProperty("base_uri")
    private String baseUri;

    public String getBaseUri() {
        return baseUri;
    }


    public static ConfigLoader load() {
        String resourcePath = "configs/config.json";
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new RuntimeException("Resource not found: " + resourcePath);
            }
            ConfigLoader config = new ObjectMapper().readValue(is, ConfigLoader.class);
            RestAssured.baseURI = config.getBaseUri();
            return config;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load or parse " + resourcePath, e);
        }
    }
}
package net.einself.dateipoodle.config;

import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix = "dateipoodle.app")
public class AppConfig {

    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}

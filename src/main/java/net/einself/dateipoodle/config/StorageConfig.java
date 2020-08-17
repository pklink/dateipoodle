package net.einself.dateipoodle.config;

import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix = "dateipoodle.storage")
public class StorageConfig {

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}

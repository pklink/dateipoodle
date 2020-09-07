package net.einself.dateipoodle.dto;

public class FileObject {

    private final String name;
    private final String originalName;

    public FileObject(String name, String originalName) {
        this.name = name;
        this.originalName = originalName;
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }
}

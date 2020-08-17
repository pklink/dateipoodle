package net.einself.dateipoodle.service;

import java.io.File;
import java.util.Optional;

public interface FileSystemService {

    Optional<File> get(String fileName);
    void delete(String fileName);
    boolean store(File file, String fileName);

}

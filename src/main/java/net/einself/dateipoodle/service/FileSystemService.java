package net.einself.dateipoodle.service;

import java.io.File;

public interface FileSystemService {

    void delete(String fileName);
    boolean store(File file, String fileName);

}

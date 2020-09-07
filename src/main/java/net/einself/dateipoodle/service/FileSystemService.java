package net.einself.dateipoodle.service;

import net.einself.dateipoodle.dto.FileObject;

import java.io.File;
import java.util.Optional;
import java.util.stream.Stream;

public interface FileSystemService {

    Optional<File> get(String fileName);
    Stream<File> getAll();
    void delete(String fileName);
    Optional<FileObject> store(File file, String fileName);

}

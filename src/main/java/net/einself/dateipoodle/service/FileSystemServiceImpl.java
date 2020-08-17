package net.einself.dateipoodle.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ApplicationScoped
public class FileSystemServiceImpl implements FileSystemService {

    @ConfigProperty(name = "dateipoodle.storage.path")
    String storagePath;

    @Override
    public void delete(String fileName) {
        final var path = Paths.get(storagePath, fileName);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean store(File file, String fileName) {
        final var dstPath = storagePath + "/" + fileName;

        try {
            Files.move(file.toPath(), Paths.get(dstPath));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}

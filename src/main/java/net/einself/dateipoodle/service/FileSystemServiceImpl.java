package net.einself.dateipoodle.service;

import net.einself.dateipoodle.config.StorageConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ApplicationScoped
public class FileSystemServiceImpl implements FileSystemService {

    private final StorageConfig storageConfig;

    @Inject
    public FileSystemServiceImpl(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }

    @Override
    public void delete(String fileName) {
        final var path = Paths.get(storageConfig.getPath(), fileName);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean store(File file, String fileName) {
        final var dstPath = Paths.get(storageConfig.getPath(), fileName);

        try {
            Files.move(file.toPath(), dstPath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}

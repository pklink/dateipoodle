package net.einself.dateipoodle.service;

import net.einself.dateipoodle.config.StorageConfig;
import net.einself.dateipoodle.dto.FileObject;
import org.apache.commons.lang3.RandomStringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class FileSystemServiceImpl implements FileSystemService {

    private final StorageConfig storageConfig;

    @Inject
    public FileSystemServiceImpl(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }

    @Override
    public Optional<File> get(String fileName) {
        Path path = Paths.get(storageConfig.getPath(), fileName);
        File file = path.toFile();

        if (file.exists()) {
            return Optional.of(file);
        }

        return Optional.empty();
    }

    @Override
    public Stream<File> getAll() {
        try {
            return Files.walk(Paths.get(storageConfig.getPath()))
                .map(Path::toFile)
                .filter(File::isFile);
        } catch (IOException e) {
            return Stream.empty();
        }
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
    public Optional<FileObject> store(File file, String originalFileName) {
        final var epochSecond = Instant.now().getEpochSecond();
        final var rndString = RandomStringUtils.randomAlphabetic(4);
        final var dstFileName = String.format("%d-%s-%s", epochSecond, rndString, originalFileName);
        final var dstPath = Paths.get(storageConfig.getPath(), dstFileName);

        try {
            Files.move(file.toPath(), dstPath);
            return Optional.of(new FileObject(dstFileName, originalFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}

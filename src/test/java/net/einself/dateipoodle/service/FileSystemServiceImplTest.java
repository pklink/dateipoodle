package net.einself.dateipoodle.service;

import net.einself.dateipoodle.config.StorageConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class FileSystemServiceImplTest {

    private FileSystemServiceImpl underTest;

    StorageConfig storageConfig;
    String tmpDir;

    @BeforeEach
    public void init() throws IOException {
        // given
        storageConfig = Mockito.mock(StorageConfig.class);

        // given
        tmpDir = Files.createTempDirectory("dateipoodle-test").toString();

        // given
        Mockito.when(storageConfig.getPath()).thenReturn(tmpDir);

        underTest = new FileSystemServiceImpl(storageConfig);
    }

    @Test
    void testDelete_exists() throws IOException {
        // given
        final var file = Files.createFile(Paths.get(tmpDir, "dateipoodle-test.delete-exists"));

        // when
        underTest.delete("dateipoodle-test.delete-exists");

        // then
        Assertions.assertTrue(Files.notExists(Paths.get(tmpDir, "dateipoodle.test")));
    }

    @Test
    void testDelete_notExists() {
        // when
        underTest.delete("dateipoodle-test.delete-not-exists");

        // then
        Assertions.assertTrue(Files.notExists(Paths.get(tmpDir, "dateipoodle-notexists.test")));
    }

    @Test
    void store() throws IOException {
        // given
        final var file = Files.createTempFile("dateipoodle-test", "store.src").toFile();

        // when
        underTest.store(file, "dateipoodle-test.store.dst");

        // then
        Files.exists(Paths.get(tmpDir, "dateipoodle-test.store.dst"));
    }

}

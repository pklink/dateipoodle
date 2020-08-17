package net.einself.dateipoodle.service;

import net.einself.dateipoodle.config.StorageConfig;
import org.mockito.Mockito;

class FileSystemServiceImplTest {

    private FileSystemServiceImpl underTest;

    StorageConfig storageConfig;

    public void init() {
        storageConfig = Mockito.mock(StorageConfig.class);
        underTest = new FileSystemServiceImpl(storageConfig);
    }

    //@Test
    void testDelete_exists() {

    }

    //@Test
    void store() {
    }

}

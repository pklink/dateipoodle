package net.einself.dateipoodle.service;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;

@QuarkusTest
class FileItemServiceImplTest {

    FileItemServiceImpl underTest;

    @InjectMock
    FileItemRepository fileItemRepository;

    @BeforeEach
    public void init() {
        underTest = new FileItemServiceImpl(fileItemRepository);
    }




}

package net.einself.dateipoodle.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import net.einself.dateipoodle.service.FileService;
import org.mockito.Mockito;

@QuarkusTest
class DownloadResourceTest {

    @InjectMock
    FileService fileService;

    public void testOk() {
        Mockito.when(fileService.exists("foo")).thenReturn(true);
    }


}

package net.einself.dateipoodle.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import net.einself.dateipoodle.service.FileItemService;
import org.mockito.Mockito;

@QuarkusTest
class DownloadResourceTest {

    @InjectMock
    FileItemService fileItemService;

    public void testOk() {
        Mockito.when(fileItemService.exists("foo")).thenReturn(true);
    }


}

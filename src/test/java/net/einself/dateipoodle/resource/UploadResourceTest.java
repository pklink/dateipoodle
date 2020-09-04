package net.einself.dateipoodle.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import net.einself.dateipoodle.domain.FileItem;
import net.einself.dateipoodle.service.FileItemService;
import net.einself.dateipoodle.service.FileSystemService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest
@TestSecurity(user = "admin", roles = "admin")
public class UploadResourceTest {

    @InjectMock
    FileItemService fileItemService;

    @InjectMock
    FileSystemService fileSystemService;

    @Test
    public void test200() throws IOException {
        // given
        final var fileItem = new FileItem("foo", "bar.jpg");

        // given
        final var tmpFile = File.createTempFile("dateipoodle", "test");
        tmpFile.deleteOnExit();

        // given
        Mockito.when(fileSystemService.store(ArgumentMatchers.any(), anyString())).thenReturn(true);

        // given
        Mockito.when(fileItemService.create(any(FileItem.class))).thenReturn(fileItem);

        given()
            .multiPart("file", tmpFile)
            .multiPart("fileName", "test.jpg")
            .when().post("/files")
            .then()
            .statusCode(200)
            .body("id", is("foo"))
            .body("name", is("bar.jpg"));
    }

    @Test
    public void test400_emptyForm() {
        given()
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .when().post("/files")
            .then()
            .statusCode(400);
    }

    @Test
    public void test400_file() {
        given()
            .multiPart("fileName", "test.jpg")
            .when().post("/files")
            .then()
            .statusCode(400);
    }

    @Test
    public void test400_fileName() throws IOException {
        // given
        final var tmpFile = File.createTempFile("dateipoodle", "test");
        tmpFile.deleteOnExit();

        given()
            .multiPart("file", tmpFile)
            .when().post("/files")
            .then()
            .statusCode(400);
    }

    @Test
    public void test500() throws IOException {
        // given
        final var fileItem = new FileItem("foo", "bar.jpg");

        // given
        Mockito.when(fileSystemService.store(ArgumentMatchers.any(), anyString())).thenReturn(false);

        // given
        final var tmpFile = File.createTempFile("dateipoodle", "test");
        tmpFile.deleteOnExit();

        // given
        Mockito.when(fileItemService.create(any(FileItem.class))).thenReturn(fileItem);

        // then
        given()
            .multiPart("file", tmpFile)
            .multiPart("fileName", "test.jpg")
            .when().post("/files")
            .then()
            .statusCode(500);
        Mockito.verify(fileItemService).delete(fileItem);
    }

}

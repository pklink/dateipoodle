package net.einself.dateipoodle;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import net.einself.dateipoodle.domain.FileItem;
import net.einself.dateipoodle.dto.UploadFileRequest;
import net.einself.dateipoodle.service.FileService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
public class UploadResourceTest {

    @InjectMock
    FileService fileService;

    @Test
    public void testOk() throws IOException {
        // given
        final var file = new FileItem();
        file.setId("foo");
        file.setName("bar.jpg");

        // given
        final var tmpFile = File.createTempFile("dateipoodle", "test");
        tmpFile.deleteOnExit();

        // when
        Mockito.when(fileService.create(any(UploadFileRequest.class))).thenReturn(file);

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

}

package net.einself.dateipoodle.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import net.einself.dateipoodle.dto.FileObject;
import net.einself.dateipoodle.service.FileSystemService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest
@TestSecurity(user = "admin", roles = "admin")
public class FilesResourceTest {

    @InjectMock
    FileSystemService fileSystemService;

    @Test
    public void testUpload_Created() throws IOException {
        // given
        final var tmpFile = File.createTempFile("dateipoodle", "test");
        tmpFile.deleteOnExit();

        // given
        Mockito.when(fileSystemService.store(ArgumentMatchers.any(), anyString()))
            .thenReturn(Optional.of(new FileObject("123456-abcd-foo.jpg", "foo.jpg")));

        given()
            .multiPart("file", tmpFile)
            .multiPart("fileName", "test.jpg")
            .when().post("/files")
            .then()
            .header("Location", "http://localhost:8080/files/123456-abcd-foo.jpg")
            .statusCode(Response.Status.CREATED.getStatusCode())
            .body("name", is("123456-abcd-foo.jpg"))
            .body("originalName", is("foo.jpg"));
    }

    @Test
    public void testUpload_400_BadRequest_empty() {
        given()
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .when().post("/files")
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testUpload_BadRequest_file() {
        given()
            .multiPart("fileName", "test.jpg")
            .when().post("/files")
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testUpload_BadRequest_fileName() throws IOException {
        // given
        final var tmpFile = File.createTempFile("dateipoodle", "test");
        tmpFile.deleteOnExit();

        given()
            .multiPart("file", tmpFile)
            .when().post("/files")
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testUpload_InternalServerError() throws IOException {
        // given
        Mockito.when(fileSystemService.store(ArgumentMatchers.any(), anyString()))
            .thenReturn(Optional.empty());

        // given
        final var tmpFile = File.createTempFile("dateipoodle", "test");
        tmpFile.deleteOnExit();

        // then
        given()
            .multiPart("file", tmpFile)
            .multiPart("fileName", "test.jpg")
            .when().post("/files")
            .then()
            .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }

}

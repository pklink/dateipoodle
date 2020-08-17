package net.einself.dateipoodle.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import net.einself.dateipoodle.domain.FileItem;
import net.einself.dateipoodle.service.FileItemService;
import net.einself.dateipoodle.service.FileSystemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static io.restassured.RestAssured.given;

@QuarkusTest
class DownloadResourceTest {

    @InjectMock
    FileItemService fileItemService;

    @InjectMock
    FileSystemService fileSystemService;

    @Test
    public void testOk() throws IOException {
        // given
        final var fileItem = Optional.of(new FileItem("id", "foo.jpg"));

        // given
        Mockito.when(fileItemService.findAndDeleteById("id")).thenReturn(fileItem);

        // given
        final var file = Files.createTempFile("dateipoodle", "test").toFile();

        // given
        Mockito.when(fileSystemService.get("id")).thenReturn(Optional.of(file));

        given()
            .when().get("/id")
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .header("Content-Length", "0")
            .header("Content-Type", MediaType.APPLICATION_OCTET_STREAM)
            .header("Content-Disposition", "attachment; filename=foo.jpg");
    }

    @Test
    public void testNotFound_db() {
        // given
        Mockito.when(fileItemService.findAndDeleteById("id")).thenReturn(Optional.empty());

        given()
            .when().get("/id")
            .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void testNotFound_fs() {
        // given
        final var fileItem = Optional.of(new FileItem("id", "foo.jpg"));

        // given
        Mockito.when(fileItemService.findAndDeleteById("id")).thenReturn(fileItem);

        // given
        Mockito.when(fileSystemService.get("id")).thenReturn(Optional.empty());

        given()
            .when().get("/id")
            .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

}

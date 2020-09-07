package net.einself.dateipoodle.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
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
    FileSystemService fileSystemService;

    @Test
    public void testDownload_Ok() throws IOException {
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
    public void testDownload_NotFound() {
        // given
        Mockito.when(fileSystemService.get("id")).thenReturn(Optional.empty());

        given()
            .when().get("/id")
            .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

}

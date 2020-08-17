package net.einself.dateipoodle.resource;

import net.einself.dateipoodle.service.FileItemService;
import net.einself.dateipoodle.service.FileSystemService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/")
public class DownloadResource {

    private final FileItemService fileItemService;
    private final FileSystemService fileSystemService;

    @ConfigProperty(name = "dateipoodle.storage.path")
    String storagePath;

    @Inject
    public DownloadResource(FileItemService fileItemService, FileSystemService fileSystemService) {
        this.fileItemService = fileItemService;
        this.fileSystemService = fileSystemService;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response download(@PathParam("id") String id) {
        if (!fileItemService.exists(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            final var path = Paths.get(storagePath, id);
            final var bytes = Files.readAllBytes(path);
            fileSystemService.delete(id);
            return Response.ok(bytes)
                    .header("Content-Length", bytes.length)
                    .header("Content-Type", MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=bla.jpg")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}

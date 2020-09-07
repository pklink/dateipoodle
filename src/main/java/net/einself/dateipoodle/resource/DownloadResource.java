package net.einself.dateipoodle.resource;

import net.einself.dateipoodle.service.FileSystemService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class DownloadResource {

    private final FileSystemService fileSystemService;

    @Inject
    public DownloadResource(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public Response download(@PathParam("id") String id) {
        return fileSystemService.get(id)
            .map(file -> Response.ok(file)
                .header("Content-Length", file.length())
                .header("Content-Type", MediaType.APPLICATION_OCTET_STREAM)
                // TODO
                .header("Content-Disposition", "attachment; filename=" + "foo.jpg")
                .build()
            ).orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

}

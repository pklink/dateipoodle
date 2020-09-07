package net.einself.dateipoodle.resource;

import net.einself.dateipoodle.config.AppConfig;
import net.einself.dateipoodle.dto.FileObject;
import net.einself.dateipoodle.dto.request.UploadFileRequest;
import net.einself.dateipoodle.service.FileSystemService;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/files")
public class FilesResource {

    private final AppConfig appConfig;
    private final FileSystemService fileSystemService;

    @Inject
    public FilesResource(AppConfig appConfig, FileSystemService fileSystemService) {
        this.appConfig = appConfig;
        this.fileSystemService = fileSystemService;
    }

    // TODO test
    URI createDownloadURI(FileObject fileObject) {
        final var url = StringUtils.stripEnd(appConfig.getBaseUrl(), "/") + "/" + fileObject.getName();
        return URI.create(url);
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(@Valid @MultipartForm UploadFileRequest uploadFileRequest) {
        // store file in file system
        return fileSystemService.store(uploadFileRequest.getFile(), uploadFileRequest.getFileName())

            // return file object
            .map(fileObject -> Response
                .created(createDownloadURI(fileObject))
                .entity(fileObject)
                .build()
            )

            // return error message
            .orElseGet(() -> Response
                .serverError()
                .entity(Map.of("message", "Cannot write file on file system"))
                .build()
            );
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        final var files = fileSystemService.getAll()
            .sorted(Collections.reverseOrder())
            .map(File::getName)
            .collect(Collectors.toList());

        return Response.ok(files).build();
    }

}

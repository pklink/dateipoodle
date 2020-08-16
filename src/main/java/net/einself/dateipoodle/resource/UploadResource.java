package net.einself.dateipoodle.resource;

import net.einself.dateipoodle.domain.File;
import net.einself.dateipoodle.dto.UploadFileRequest;
import net.einself.dateipoodle.service.FileService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/")
public class UploadResource {

    @Inject
    FileService fileService;

    @ConfigProperty(name = "dateipoodle.storage.path")
    String storagePath;

    @POST
    @Path("/files")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public File upload(@Valid @MultipartForm UploadFileRequest uploadFileRequest) {
        final var file1 = fileService.create(uploadFileRequest);

        try {
            final var dstPath = storagePath + "/" + file1.id;
            Files.move(uploadFileRequest.file.toPath(), Paths.get(dstPath));
        } catch (IOException e) {
            fileService.delete(file1);
        }

        return file1;
    }

}
package net.einself.dateipoodle.resource;

import net.einself.dateipoodle.converter.Converter;
import net.einself.dateipoodle.converter.UploadFileRequestToFileItemConverter;
import net.einself.dateipoodle.domain.FileItem;
import net.einself.dateipoodle.dto.UploadFileRequest;
import net.einself.dateipoodle.service.FileItemService;
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

    private final Converter<UploadFileRequest, FileItem> dtoConverter;
    private final FileItemService fileItemService;

    @ConfigProperty(name = "dateipoodle.storage.path")
    String storagePath;

    @Inject
    public UploadResource(UploadFileRequestToFileItemConverter dtoConverter, FileItemService fileItemService) {
        this.dtoConverter = dtoConverter;
        this.fileItemService = fileItemService;
    }

    @POST
    @Path("/files")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public FileItem upload(@Valid @MultipartForm UploadFileRequest uploadFileRequest) {
        // convert dto to entity
        var entity = dtoConverter.one(uploadFileRequest);

        // persist entity
        entity = fileItemService.create(entity);

        try {
            final var dstPath = storagePath + "/" + entity.getId();
            Files.move(uploadFileRequest.getFile().toPath(), Paths.get(dstPath));
        } catch (IOException e) {
            fileItemService.delete(entity);
        }

        return entity;
    }

}

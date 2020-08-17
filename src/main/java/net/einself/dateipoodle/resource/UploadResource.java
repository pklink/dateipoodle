package net.einself.dateipoodle.resource;

import net.einself.dateipoodle.converter.Converter;
import net.einself.dateipoodle.converter.UploadFileRequestToFileItemConverter;
import net.einself.dateipoodle.domain.FileItem;
import net.einself.dateipoodle.dto.UploadFileRequest;
import net.einself.dateipoodle.service.FileItemService;
import net.einself.dateipoodle.service.FileSystemService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/")
public class UploadResource {

    private final Converter<UploadFileRequest, FileItem> dtoConverter;
    private final FileItemService fileItemService;
    private final FileSystemService fileSystemService;

    @Inject
    public UploadResource(UploadFileRequestToFileItemConverter dtoConverter, FileItemService fileItemService, FileSystemService fileSystemService) {
        this.dtoConverter = dtoConverter;
        this.fileItemService = fileItemService;
        this.fileSystemService = fileSystemService;
    }

    @POST
    @Path("/files")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(@Valid @MultipartForm UploadFileRequest uploadFileRequest) {
        // convert dto to entity
        var entity = dtoConverter.one(uploadFileRequest);

        // persist entity
        entity = fileItemService.create(entity);

        // store file in file system
        if (!fileSystemService.store(uploadFileRequest.getFile(), entity.getId())) {
            // on error remove entity in database
            fileItemService.delete(entity);

            // and return error message
            return Response
                .serverError()
                .entity(Map.of("message", "Cannot write file on file system"))
                .build();
        }

        return Response.ok(entity).build();
    }

}

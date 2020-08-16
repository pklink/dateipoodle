package net.einself.dateipoodle.dto;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.File;

public class UploadFileRequest {

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    @NotNull
    public File file;

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    @NotBlank
    public String fileName;

}

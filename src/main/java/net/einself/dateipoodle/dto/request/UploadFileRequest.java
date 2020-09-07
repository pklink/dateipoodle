package net.einself.dateipoodle.dto.request;

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
    private File file;

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    @NotBlank
    private String fileName;

    public UploadFileRequest() { }

    public UploadFileRequest(@NotNull File file, @NotBlank String fileName) {
        this.file = file;
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

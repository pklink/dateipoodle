package net.einself.dateipoodle.service;

import net.einself.dateipoodle.domain.FileItem;
import net.einself.dateipoodle.dto.UploadFileRequest;

public interface FileService {

    void delete(String id);
    void delete(FileItem fileItem);
    boolean exists(String id);
    FileItem create(UploadFileRequest file);

}

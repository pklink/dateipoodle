package net.einself.dateipoodle.service;

import net.einself.dateipoodle.domain.File;
import net.einself.dateipoodle.dto.UploadFileRequest;

public interface FileService {

    void delete(String id);
    void delete(File file);
    boolean exists(String id);
    File create(UploadFileRequest file);

}

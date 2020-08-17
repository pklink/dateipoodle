package net.einself.dateipoodle.service;

import net.einself.dateipoodle.domain.FileItem;

public interface FileItemService {

    void delete(String id);
    void delete(FileItem fileItem);
    boolean exists(String id);
    FileItem create(FileItem file);

}

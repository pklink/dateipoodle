package net.einself.dateipoodle.service;

import net.einself.dateipoodle.domain.FileItem;

import javax.transaction.Transactional;

public interface FileItemService {

    @Transactional
    void delete(String id);

    @Transactional
    void delete(FileItem fileItem);

    boolean exists(String id);

    @Transactional
    FileItem create(FileItem file);

}

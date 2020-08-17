package net.einself.dateipoodle.service;

import net.einself.dateipoodle.domain.FileItem;

import javax.transaction.Transactional;
import java.util.Optional;

public interface FileItemService {

    @Transactional
    FileItem create(FileItem file);

    @Transactional
    void delete(String id);

    @Transactional
    void delete(FileItem fileItem);

    boolean exists(String id);
    Optional<FileItem> findAndDeleteById(String id);



}

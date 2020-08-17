package net.einself.dateipoodle.service;

import net.einself.dateipoodle.domain.FileItem;
import net.einself.dateipoodle.generator.FileItemIdGenerator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FileItemServiceImpl implements FileItemService {

    final FileItemIdGenerator fileItemIdGenerator;
    final FileItemRepository fileItemRepository;

    @Inject
    public FileItemServiceImpl(FileItemIdGenerator fileItemIdGenerator, FileItemRepository fileItemRepository) {
        this.fileItemIdGenerator = fileItemIdGenerator;
        this.fileItemRepository = fileItemRepository;
    }

    @Override
    public FileItem create(FileItem fileItem) {
        fileItem.setId(fileItemIdGenerator.generate());
        fileItemRepository.persist(fileItem);
        return fileItem;
    }

    @Override
    public void delete(String id) {
        fileItemRepository.deleteById(id);
    }

    @Override
    public void delete(FileItem fileItem) {
        fileItemRepository.delete(fileItem);
    }

    @Override
    public boolean exists(String id) {
        return fileItemRepository.findByIdOptional(id).isPresent();
    }

}

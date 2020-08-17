package net.einself.dateipoodle.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import net.einself.dateipoodle.domain.FileItem;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class FileItemServiceImpl implements FileItemService {

    final FileItemRepository fileItemRepository;

    @Inject
    public FileItemServiceImpl(FileItemRepository fileItemRepository) {
        this.fileItemRepository = fileItemRepository;
    }

    @Override
    @Transactional
    public FileItem create(FileItem fileItem) {
        fileItem.setId(generateId());
        fileItemRepository.persist(fileItem);
        return fileItem;
    }

    @Override
    @Transactional
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

    private String generateId() {
        final var id = NanoIdUtils.randomNanoId();

        if (fileItemRepository.findByIdOptional(id).isEmpty()) {
            return id;
        }

        return generateId();
    }

}

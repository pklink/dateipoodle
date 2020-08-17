package net.einself.dateipoodle.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import net.einself.dateipoodle.domain.FileItem;
import net.einself.dateipoodle.dto.UploadFileRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class FileItemServiceImpl implements FileItemService {

    @Override
    @Transactional
    public FileItem create(UploadFileRequest request) {
        final var file = new FileItem();
        file.setId(generateId());
        file.setName(request.fileName);
        FileItem.persist(file);
        return file;
    }

    @Override
    @Transactional
    public void delete(String id) {
        FileItem.deleteById(id);
    }

    @Override
    public void delete(FileItem fileItem) {
        FileItem.deleteById(fileItem.getId());
    }

    @Override
    public boolean exists(String id) {
        return FileItem.findByIdOptional(id).isPresent();
    }

    private String generateId() {
        final var id = NanoIdUtils.randomNanoId();
        if (FileItem.findById(id) == null) {
            return id;
        }
        return generateId();
    }

}

package net.einself.dateipoodle.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import net.einself.dateipoodle.domain.File;
import net.einself.dateipoodle.dto.UploadFileRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class FileServiceImpl implements FileService {

    @Override
    @Transactional
    public File create(UploadFileRequest request) {
        final var file = new File();
        file.id = generateId();
        file.name = request.fileName;
        File.persist(file);
        return file;
    }

    @Override
    @Transactional
    public void delete(String id) {
        File.deleteById(id);
    }

    @Override
    public void delete(File file) {
        File.deleteById(file.id);
    }

    @Override
    public boolean exists(String id) {
        return File.findByIdOptional(id).isPresent();
    }

    private String generateId() {
        final var id = NanoIdUtils.randomNanoId();
        if (File.findById(id) == null) {
            return id;
        }
        return generateId();
    }

}

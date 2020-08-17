package net.einself.dateipoodle.generator;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import net.einself.dateipoodle.service.FileItemService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FileItemIdGenerator {

    private final FileItemService fileItemService;

    @Inject
    public FileItemIdGenerator(FileItemService fileItemService) {
        this.fileItemService = fileItemService;
    }


    public String generate() {
        final var id = NanoIdUtils.randomNanoId();

        if (!fileItemService.exists(id)) {
            return id;
        }

        return generate();
    }

}

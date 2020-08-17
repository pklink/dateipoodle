package net.einself.dateipoodle.converter;

import net.einself.dateipoodle.domain.FileItem;
import net.einself.dateipoodle.dto.UploadFileRequest;

import javax.inject.Singleton;

@Singleton
public class UploadFileRequestToFileItemConverter extends Converter<UploadFileRequest, FileItem> {

    public UploadFileRequestToFileItemConverter() {
        super(UploadFileRequestToFileItemConverter::convert);
    }

    private static FileItem convert(UploadFileRequest dto) {
        final var entity = new FileItem();
        entity.setName(dto.getFileName());
        return entity;
    }

}

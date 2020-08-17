package net.einself.dateipoodle.converter;

import net.einself.dateipoodle.dto.UploadFileRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UploadFileRequestToFileItemConverterTest {

    UploadFileRequestToFileItemConverter underTest;

    @BeforeEach
    public void init() {
        underTest = new UploadFileRequestToFileItemConverter();
    }

    @Test
    public void testOne() throws IOException {
        // given
        final var file = Files.createTempFile("dateipoodle", "test").toFile();

        // given
        final var uploadFileRequest = new UploadFileRequest(file, "foobar.1");

        // when
        final var result = underTest.one(uploadFileRequest);

        // then
        assertNull(result.getId());
        assertEquals("foobar.1", result.getName());
    }

}

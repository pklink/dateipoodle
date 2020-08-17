package net.einself.dateipoodle.generator;

import net.einself.dateipoodle.service.FileItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

class FileItemIdGeneratorTest {

    private FileItemIdGenerator underTest;

    private FileItemService fileItemService;

    @BeforeEach
    public void init() {
        fileItemService = Mockito.mock(FileItemService.class);
        underTest = new FileItemIdGenerator(fileItemService);
    }

    @Test
    public void testGenerate_onFirstTry() {
        // when
        Mockito.when(fileItemService.exists(anyString())).thenReturn(false);

        // when
        final var result = underTest.generate();

        // then
        Assertions.assertNotNull(result);
        Mockito.verify(fileItemService, times(1)).exists(anyString());
    }

    @Test
    public void testGenerate_notOnFirstTry() {
        // when
        Mockito.when(fileItemService.exists(anyString())).thenReturn(true).thenReturn(false);

        // when
        final var result = underTest.generate();

        // then
        Assertions.assertNotNull(result);
        Mockito.verify(fileItemService, times(2)).exists(anyString());
    }

}

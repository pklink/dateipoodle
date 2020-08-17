package net.einself.dateipoodle.service;

import net.einself.dateipoodle.domain.FileItem;
import net.einself.dateipoodle.generator.FileItemIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FileItemServiceImplTest {

    FileItemServiceImpl underTest;

    FileItemIdGenerator fileItemIdGenerator;
    FileItemRepository fileItemRepository;

    @BeforeEach
    public void init() {
        // given
        fileItemIdGenerator = Mockito.mock(FileItemIdGenerator.class);
        Mockito.when(fileItemIdGenerator.generate()).thenReturn("FAKE-ID");

        // give
        fileItemRepository = Mockito.mock(FileItemRepository.class);

        underTest = new FileItemServiceImpl(fileItemIdGenerator, fileItemRepository);
    }

    @Test
    public void testCreate() {
        // given
        var fileItem = new FileItem();
        fileItem.setName("foobar.1");

        // when
        fileItem = underTest.create(fileItem);

        // then
        assertEquals("FAKE-ID", fileItem.getId());
        assertEquals("foobar.1", fileItem.getName());
    }

    @Test
    public void testDelete_id() {
        // when
        underTest.delete("id1");

        // then
        Mockito.verify(fileItemRepository).deleteById("id1");
    }

    @Test
    public void testDelete_entity() {
        // given
        var fileItem = new FileItem();
        fileItem.setId("id1");

        // when
        underTest.delete(fileItem);

        // then
        Mockito.verify(fileItemRepository).delete(fileItem);
    }

    @Test
    public void testExists_true() {
        // given
        Mockito.when(fileItemRepository.findByIdOptional("id1")).thenReturn(Optional.of(new FileItem()));

        // when
        final var result = underTest.exists("id1");

        // then
        assertTrue(result);
    }

    @Test
    public void testExists_false() {
        // given
        Mockito.when(fileItemRepository.findByIdOptional("id1")).thenReturn(Optional.empty());

        // when
        final var result = underTest.exists("id1");

        // then
        assertFalse(result);
    }

}

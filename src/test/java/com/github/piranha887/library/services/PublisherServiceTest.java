package com.github.piranha887.library.services;

import com.github.piranha887.library.convertors.DtoToPublisherConverter;
import com.github.piranha887.library.convertors.PublisherToDtoConverter;
import com.github.piranha887.library.domain.Publisher;
import com.github.piranha887.library.dto.PublisherDto;
import com.github.piranha887.library.exceptions.NotFoundException;
import com.github.piranha887.library.repositories.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PublisherServiceTest {
    public static final PublisherDto pubDto1 = new PublisherDto(1L, "Издательство1");
    public static final PublisherDto pubDto2 = new PublisherDto(2L, "Издательство2");
    public static final Publisher pub1 = new Publisher(1, "Издательство1");
    public static final Publisher pub2 = new Publisher(2, "Издательство2");

    private PublisherRepository repository;
    private PublisherService service;

    @BeforeEach
    public void before() {
        repository = mock(PublisherRepository.class);
        PublisherToDtoConverter toDtoConverter = new PublisherToDtoConverter();
        DtoToPublisherConverter toEntityConverter = new DtoToPublisherConverter(repository);
        service = new PublisherService(repository, toDtoConverter, toEntityConverter);
    }

    @Test
    void getById() {
        when(repository.findById(1L)).thenReturn(Optional.of(pub1));
        assertEquals(pubDto1, service.getById(1));
    }

    @Test
    void getById_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.getById(1));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Publisher with id 1 not found", exception.getMessage());
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(pub1, pub2));
        assertEquals(Arrays.asList(pubDto1, pubDto2), service.findAll());
    }

    @Test
    void add() {
        when(repository.save(any())).thenReturn(pub2);
        assertEquals(2, service.add(new PublisherDto()));
    }

    @Test
    void update() {
        when(repository.findById(2L)).thenReturn(Optional.of(pub2));
        ArgumentCaptor<Publisher> captor = ArgumentCaptor.forClass(Publisher.class);
        PublisherDto dtoUpd = new PublisherDto(null, "Издательство22");

        service.update(2, dtoUpd);

        verify(repository).save(captor.capture());
        assertEquals(new Publisher(2, "Издательство22"), captor.getValue());
    }

    @Test
    void delete() {
        when(repository.existsById(2L)).thenReturn(true);

        service.delete(2);

        verify(repository).deleteById(2L);
    }
}
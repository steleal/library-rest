package com.github.piranha887.library.services;

import com.github.piranha887.library.convertors.AuthorToDtoConverter;
import com.github.piranha887.library.convertors.DtoToAuthorConverter;
import com.github.piranha887.library.domain.Author;
import com.github.piranha887.library.dto.AuthorDto;
import com.github.piranha887.library.exceptions.NotFoundException;
import com.github.piranha887.library.repositories.AuthorRepository;
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

class AuthorServiceTest {
    public static final AuthorDto pushkinDto = new AuthorDto(1L, "Пушкин", 1800);
    public static final AuthorDto lermontovDto = new AuthorDto(2L, "Лермонтов", 1801);
    public static final Author pushkin = new Author(1, "Пушкин", 1800);
    public static final Author lermontov = new Author(2, "Лермонтов", 1801);

    private AuthorRepository authorRepository;
    private AuthorService service;

    @BeforeEach
    public void before() {
        authorRepository = mock(AuthorRepository.class);
        AuthorToDtoConverter toDtoConverter = new AuthorToDtoConverter();
        DtoToAuthorConverter toAuthorConverter = new DtoToAuthorConverter(authorRepository);
        service = new AuthorService(authorRepository, toDtoConverter, toAuthorConverter);
    }

    @Test
    void getById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(pushkin));
        assertEquals(pushkinDto, service.getById(1));
    }

    @Test
    void getById_whenNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.getById(1));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Author with id 1 not found", exception.getMessage());
    }

    @Test
    void findAll() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList(pushkin, lermontov));
        assertEquals(Arrays.asList(pushkinDto, lermontovDto), service.findAll());
    }

    @Test
    void add() {
        when(authorRepository.save(any())).thenReturn(lermontov);
        assertEquals(2, service.add(new AuthorDto()));
    }

    @Test
    void update() {
        when(authorRepository.findById(2L)).thenReturn(Optional.of(lermontov));
        ArgumentCaptor<Author> captor = ArgumentCaptor.forClass(Author.class);
        AuthorDto lermontUpd = new AuthorDto(null, null, 1900);

        service.update(2, lermontUpd);

        verify(authorRepository).save(captor.capture());
        assertEquals(new Author(2, "Лермонтов", 1900), captor.getValue());
    }

    @Test
    void delete() {
        when(authorRepository.existsById(2L)).thenReturn(true);

        service.delete(2);

        verify(authorRepository).deleteById(2L);
    }
}
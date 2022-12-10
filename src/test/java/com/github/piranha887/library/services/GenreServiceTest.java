package com.github.piranha887.library.services;

import com.github.piranha887.library.convertors.DtoToGenreConverter;
import com.github.piranha887.library.convertors.GenreToDtoConverter;
import com.github.piranha887.library.domain.Genre;
import com.github.piranha887.library.dto.GenreDto;
import com.github.piranha887.library.exceptions.NotFoundException;
import com.github.piranha887.library.repositories.GenreRepository;
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

class GenreServiceTest {
    public static final GenreDto novelDto = new GenreDto(1L, "Роман");
    public static final GenreDto storyDto = new GenreDto(2L, "Повесть");
    public static final Genre novel = new Genre(1, "Роман");
    public static final Genre story = new Genre(2, "Повесть");

    private GenreRepository repository;
    private GenreService service;

    @BeforeEach
    public void before() {
        repository = mock(GenreRepository.class);
        GenreToDtoConverter toDtoConverter = new GenreToDtoConverter();
        DtoToGenreConverter toEntityConverter = new DtoToGenreConverter(repository);
        service = new GenreService(repository, toDtoConverter, toEntityConverter);
    }

    @Test
    void getById() {
        when(repository.findById(1L)).thenReturn(Optional.of(novel));
        assertEquals(novelDto, service.getById(1));
    }

    @Test
    void getById_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.getById(1));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Genre with id 1 not found", exception.getMessage());
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(novel, story));
        assertEquals(Arrays.asList(novelDto, storyDto), service.findAll());
    }

    @Test
    void add() {
        when(repository.save(any())).thenReturn(story);
        assertEquals(2, service.add(new GenreDto()));
    }

    @Test
    void update() {
        when(repository.findById(2L)).thenReturn(Optional.of(story));
        ArgumentCaptor<Genre> captor = ArgumentCaptor.forClass(Genre.class);
        GenreDto dtoUpd = new GenreDto(null, "StoryUpd");

        service.update(2, dtoUpd);

        verify(repository).save(captor.capture());
        assertEquals(new Genre(2, "StoryUpd"), captor.getValue());
    }

    @Test
    void delete() {
        when(repository.existsById(2L)).thenReturn(true);

        service.delete(2);

        verify(repository).deleteById(2L);
    }
}
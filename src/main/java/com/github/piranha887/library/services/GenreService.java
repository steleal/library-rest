package com.github.piranha887.library.services;

import com.github.piranha887.library.convertors.DtoToGenreConverter;
import com.github.piranha887.library.convertors.GenreToDtoConverter;
import com.github.piranha887.library.domain.Genre;
import com.github.piranha887.library.dto.GenreDto;
import com.github.piranha887.library.exceptions.NotFoundException;
import com.github.piranha887.library.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository repository;
    private final GenreToDtoConverter toDto;
    private final DtoToGenreConverter toGenre;

    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        return repository.findAll().stream()
                .map(toDto::convert)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GenreDto getById(long genreId) {
        return repository.findById(genreId)
                .map(toDto::convert)
                .orElseThrow(() -> new NotFoundException("Genre", genreId));
    }

    public long add(GenreDto genreDto) {
        Genre save = repository.save(toGenre.convert(genreDto));
        return save.getId();
    }

    public void update(long genreId, GenreDto genreDto) {
        genreDto.setId(genreId);
        repository.save(toGenre.convert(genreDto));
    }

    public void delete(long genreId) {
        if (repository.existsById(genreId)) {
            repository.deleteById(genreId);
        } else {
            throw new NotFoundException("Genre", genreId);
        }
    }

}

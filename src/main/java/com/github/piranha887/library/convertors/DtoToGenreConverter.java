package com.github.piranha887.library.convertors;

import com.github.piranha887.library.domain.Genre;
import com.github.piranha887.library.dto.GenreDto;
import com.github.piranha887.library.exceptions.NotFoundException;
import com.github.piranha887.library.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoToGenreConverter implements Converter<GenreDto, Genre> {
    private final GenreRepository repository;

    @Override
    @NonNull
    public Genre convert(GenreDto source) {
        Long id = source.getId();
        Genre genre;
        if (id == null || id == 0) {
            genre = new Genre();
        } else {
            genre = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Genre", id));
        }
        genre.setName(source.getName());
        return genre;
    }
}

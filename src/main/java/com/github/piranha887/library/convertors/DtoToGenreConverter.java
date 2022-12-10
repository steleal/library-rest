package com.github.piranha887.library.convertors;

import com.github.piranha887.library.domain.Genre;
import com.github.piranha887.library.dto.GenreDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToGenreConverter implements Converter<GenreDto, Genre> {
    @Override
    public Genre convert(GenreDto source) {
        return new Genre(source.getId() == null ? 0 : source.getId(),
                source.getName());
    }
}

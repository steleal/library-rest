package com.github.piranha887.library.convertors;

import com.github.piranha887.library.domain.Genre;
import com.github.piranha887.library.dto.GenreDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GenreToDtoConverter implements Converter<Genre, GenreDto> {
    @Override
    public GenreDto convert(Genre source) {
        return new GenreDto(source.getId(), source.getName());
    }
}

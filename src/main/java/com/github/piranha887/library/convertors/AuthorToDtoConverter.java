package com.github.piranha887.library.convertors;

import com.github.piranha887.library.domain.Author;
import com.github.piranha887.library.dto.AuthorDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuthorToDtoConverter implements Converter<Author, AuthorDto> {
    @Override
    public AuthorDto convert(Author source) {
        return new AuthorDto(
                source.getId(),
                source.getFullName(),
                source.getBirthday()
        );
    }
}

package com.github.piranha887.library.convertors;

import com.github.piranha887.library.domain.Author;
import com.github.piranha887.library.dto.AuthorDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToAuthorConverter implements Converter<AuthorDto, Author> {
    @Override
    public Author convert(AuthorDto source) {
        return new Author(
                source.getId(),
                source.getFullName(),
                source.getBirthday()
        );
    }
}

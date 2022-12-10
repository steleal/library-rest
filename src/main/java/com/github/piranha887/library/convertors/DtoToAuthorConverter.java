package com.github.piranha887.library.convertors;

import com.github.piranha887.library.domain.Author;
import com.github.piranha887.library.dto.AuthorDto;
import com.github.piranha887.library.exceptions.NotFoundException;
import com.github.piranha887.library.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoToAuthorConverter implements Converter<AuthorDto, Author> {
    private final AuthorRepository repository;

    @Override
    @NonNull
    public Author convert(AuthorDto source) {
        Long id = source.getId();
        Author author;
        if (id == null || id == 0) {
            author = new Author();
        } else {
            author = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Author", id));
        }
        String fullName = source.getFullName();
        if (fullName != null) {
            author.setFullName(fullName);
        }
        Integer birthday = source.getBirthday();
        if (birthday != null) {
            author.setBirthday(birthday);
        }
        return author;
    }
}

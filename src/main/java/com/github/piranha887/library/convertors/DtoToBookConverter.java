package com.github.piranha887.library.convertors;

import com.github.piranha887.library.domain.Book;
import com.github.piranha887.library.dto.BookDto;
import com.github.piranha887.library.repositories.AuthorRepository;
import com.github.piranha887.library.repositories.GenreRepository;
import com.github.piranha887.library.repositories.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoToBookConverter implements Converter<BookDto, Book> {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;

    @Override
    public Book convert(BookDto source) {
        return new Book(
                source.getId(),
                source.getTitle(),
                authorRepository.findById(source.getAuthorId()).get(),
                genreRepository.findById(source.getGenreId()).get(),
                publisherRepository.findById(source.getPublisherId()).get(),
                source.getPublishYear()
        );
    }
}

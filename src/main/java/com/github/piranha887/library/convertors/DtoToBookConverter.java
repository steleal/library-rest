package com.github.piranha887.library.convertors;

import com.github.piranha887.library.domain.Book;
import com.github.piranha887.library.dto.BookDto;
import com.github.piranha887.library.exceptions.NotFoundException;
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
        Long authorId = source.getAuthorId();
        Long genreId = source.getGenreId();
        Long pubId = source.getPublisherId();
        return new Book(
                source.getId() == null ? 0 : source.getId(),
                source.getTitle(),
                authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException("Author", authorId)),
                genreRepository.findById(genreId).orElseThrow(() -> new NotFoundException("Genre", genreId)),
                publisherRepository.findById(pubId).orElseThrow(() -> new NotFoundException("Publisher", pubId)),
                source.getPublishYear()
        );
    }
}

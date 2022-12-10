package com.github.piranha887.library.convertors;

import com.github.piranha887.library.domain.Author;
import com.github.piranha887.library.domain.Book;
import com.github.piranha887.library.domain.Genre;
import com.github.piranha887.library.domain.Publisher;
import com.github.piranha887.library.dto.BookDto;
import com.github.piranha887.library.exceptions.NotFoundException;
import com.github.piranha887.library.repositories.AuthorRepository;
import com.github.piranha887.library.repositories.BookRepository;
import com.github.piranha887.library.repositories.GenreRepository;
import com.github.piranha887.library.repositories.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoToBookConverter implements Converter<BookDto, Book> {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;

    @Override
    @NonNull
    public Book convert(BookDto source) {
        Long id = source.getId();
        Book book;
        if (id == null || id == 0) {
            book = new Book();
        } else {
            book = bookRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Book", id));
        }

        if (source.getTitle() != null) {
            book.setTitle(source.getTitle());
        }
        if (source.getPublishYear() != null) {
            book.setPublishYear(source.getPublishYear());
        }

        Long authorId = source.getAuthorId();
        if (authorId != null) {
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new NotFoundException("Author", authorId));
            book.setAuthor(author);
        }

        Long genreId = source.getGenreId();
        if (genreId != null) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new NotFoundException("Genre", genreId));
            book.setGenre(genre);
        }

        Long publisherId = source.getPublisherId();
        if (publisherId != null) {
            Publisher pub = publisherRepository.findById(publisherId)
                    .orElseThrow(() -> new NotFoundException("Publisher", publisherId));
            book.setPublisher(pub);
        }

        return book;
    }
}

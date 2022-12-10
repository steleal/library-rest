package com.github.piranha887.library.services;

import com.github.piranha887.library.convertors.BookToDtoConverter;
import com.github.piranha887.library.convertors.DtoToBookConverter;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final BookToDtoConverter toDto;
    private final DtoToBookConverter toBook;

    private final GenreRepository genreRepo;
    private final AuthorRepository authorRepo;
    private final PublisherRepository pubRepo;


    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return repository.findAll().stream()
                .map(toDto::convert)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookDto getById(long bookId) {
        return repository.findById(bookId)
                .map(toDto::convert)
                .orElseThrow(() -> new NotFoundException("Book", bookId));
    }

    public long add(BookDto bookDto) {
        Book book = toBook.convert(bookDto);
        book.setId(0);
        Book save = repository.save(book);
        return save.getId();
    }

    public void update(long bookId, BookDto bookDto) {
        Book book = repository.findById(bookId).orElseThrow(() -> new NotFoundException("Book", bookId));
        updateFields(book, bookDto);
        repository.save(book);
    }

    public void delete(long bookId) {
        if (repository.existsById(bookId)) {
            repository.deleteById(bookId);
        } else {
            throw new NotFoundException("Book", bookId);
        }
    }

    private void updateFields(Book book, BookDto bookDto) {
        if (bookDto != null) {
            if (bookDto.getTitle() != null) {
                book.setTitle(bookDto.getTitle());
            }
            Long authorId = bookDto.getAuthorId();
            if (authorId != null) {
                Author author = authorRepo.findById(authorId)
                        .orElseThrow(() -> new NotFoundException("Author", authorId));
                book.setAuthor(author);
            }
            Long genreId = bookDto.getGenreId();
            if (genreId != null) {
                Genre genre = genreRepo.findById(genreId)
                        .orElseThrow(() -> new NotFoundException("Genre", genreId));
                book.setGenre(genre);
            }
            Long publisherId = bookDto.getPublisherId();
            if (publisherId != null) {
                Publisher pub = pubRepo.findById(publisherId)
                        .orElseThrow(() -> new NotFoundException("Publisher", publisherId));
                book.setPublisher(pub);
            }

            if (bookDto.getPublishYear() != null) {
                book.setPublishYear(bookDto.getPublishYear());
            }
        }
    }
}

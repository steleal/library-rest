package com.github.piranha887.library.services;

import com.github.piranha887.library.convertors.BookToDtoConverter;
import com.github.piranha887.library.convertors.DtoToBookConverter;
import com.github.piranha887.library.domain.Book;
import com.github.piranha887.library.dto.BookDto;
import com.github.piranha887.library.exceptions.NotFoundException;
import com.github.piranha887.library.repositories.BookRepository;
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
        Book save = repository.save(toBook.convert(bookDto));
        return save.getId();
    }

    public void update(long bookId, BookDto bookDto) {
        bookDto.setId(bookId);
        repository.save(toBook.convert(bookDto));
    }

    public void delete(long bookId) {
        if (repository.existsById(bookId)) {
            repository.deleteById(bookId);
        } else {
            throw new NotFoundException("Book", bookId);
        }
    }

}

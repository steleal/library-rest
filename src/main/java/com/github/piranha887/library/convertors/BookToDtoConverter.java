package com.github.piranha887.library.convertors;

import com.github.piranha887.library.domain.Book;
import com.github.piranha887.library.dto.BookDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookToDtoConverter implements Converter<Book, BookDto> {

    @Override
    public BookDto convert(Book source) {
        return new BookDto(
                source.getId(),
                source.getTitle(),
                source.getAuthor().getFullName(),
                source.getAuthor().getId(),
                source.getGenre().getName(),
                source.getGenre().getId(),
                source.getPublisher().getName(),
                source.getPublisher().getId(),
                source.getPublishYear()
        );
    }
}

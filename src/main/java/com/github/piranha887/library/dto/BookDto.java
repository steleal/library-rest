package com.github.piranha887.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private long id;
    @NotBlank(message = "Наименование не должно быть пустым")
    private String title;
    private String author;
    @NotNull(message = "Укажите автора")
    private Long authorId;
    private String genre;
    @NotNull(message = "Укажите жанр")
    private Long genreId;
    private String publisher;
    @NotNull(message = "Укажите издателя")
    private Long publisherId;
    private Integer publishYear;
}

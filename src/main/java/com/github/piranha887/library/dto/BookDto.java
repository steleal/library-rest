package com.github.piranha887.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private Long authorId;
    private String authorName;
    private Long genreId;
    private String genreName;
    private Long publisherId;
    private String publisherName;
    private Integer publishYear;
}

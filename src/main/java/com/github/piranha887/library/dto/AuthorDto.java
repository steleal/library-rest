package com.github.piranha887.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    private Long id;
    private String fullName;
    private Integer birthday;

    @Override
    public String toString() {
        return fullName;
    }

}

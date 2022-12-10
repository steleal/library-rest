package com.github.piranha887.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    private long id;
    @NotBlank(message = "ФИО автора не должно быть пустым")
    private String fullName;
    private Integer birthday;

    @Override
    public String toString() {
        return fullName;
    }

}

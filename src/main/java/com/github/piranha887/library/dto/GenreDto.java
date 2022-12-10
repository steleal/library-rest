package com.github.piranha887.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
    private long id;
    @NotBlank(message = "Название не должно быть пустым")
    private String name;

    @Override
    public String toString() {
        return name;
    }
}

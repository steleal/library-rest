package com.github.piranha887.library.controllers;

import com.github.piranha887.library.dto.GenreDto;
import com.github.piranha887.library.services.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/genres", produces = MediaType.APPLICATION_JSON_VALUE)
public class GenreController extends BaseController<GenreDto> {
    private final GenreService service;

    @Override
    @GetMapping()
    public List<GenreDto> getAll() {
        return service.findAll();
    }

    @Override
    @GetMapping(path = "/{id}")
    public GenreDto getById(@PathVariable long id) {
        return service.getById(id);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public long add(@RequestBody GenreDto dto) {
        return service.add(dto);
    }

    @Override
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable long id, @RequestBody GenreDto dto) {
        service.update(id, dto);
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}

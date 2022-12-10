package com.github.piranha887.library.controllers;

import com.github.piranha887.library.dto.BookDto;
import com.github.piranha887.library.services.BookService;
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
@RequestMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController extends BaseController<BookDto> {
    private final BookService service;

    @Override
    @GetMapping()
    public List<BookDto> getAll() {
        return service.findAll();
    }

    @Override
    @GetMapping(path = "/{id}")
    public BookDto getById(@PathVariable long id) {
        return service.getById(id);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public long add(@RequestBody BookDto dto) {
        return service.add(dto);
    }

    @Override
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable long id, @RequestBody BookDto dto) {
        service.update(id, dto);
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}

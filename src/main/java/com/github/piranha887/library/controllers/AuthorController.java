package com.github.piranha887.library.controllers;

import com.github.piranha887.library.dto.AuthorDto;
import com.github.piranha887.library.services.AuthorService;
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
@RequestMapping(value = "/authors", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController extends BaseController<AuthorDto> {

    private final AuthorService service;

    @GetMapping()
    public List<AuthorDto> getAll() {
        return service.findAll();
    }

    @GetMapping(path = "/{id}")
    public AuthorDto getById(@PathVariable(value = "id") long authorId) {
        return service.getById(authorId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public long add(@RequestBody AuthorDto authorDto) {
        return service.add(authorDto);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable(value = "id") long authorId, @RequestBody AuthorDto authorDto) {
        service.update(authorId, authorDto);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(value = "id") long authorId) {
        service.delete(authorId);
    }

}

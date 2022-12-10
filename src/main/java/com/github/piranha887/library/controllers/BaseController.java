package com.github.piranha887.library.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

abstract class BaseController<E> {

    @GetMapping
    abstract public List<E> getAll();

        @GetMapping(path = "/{id}")
    abstract public E getById(@PathVariable(value = "id") long id);

    @PostMapping
    abstract public long add(@RequestBody E dto);

    @PutMapping("/{id}")
    abstract public void update(@PathVariable(value = "id") long id, @RequestBody E dto) ;

    @DeleteMapping(path = "/{id}")
    abstract public void delete(@PathVariable(value = "id") long id);

}

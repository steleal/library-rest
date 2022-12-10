package com.github.piranha887.library.controllers;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

abstract class WebController<E> {

    abstract public String list(Model model);

    @GetMapping("/new")
    abstract public String add(Model model);

    @GetMapping("/edit")
    abstract public String edit(@RequestParam Long id, Model model);

    @PostMapping("/save")
    abstract public String save(E entity, BindingResult result, Model model);

    @GetMapping("/edit")
    abstract public String delete(@RequestParam long id, Model model);

}

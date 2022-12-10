package com.github.piranha887.library.controllers;

import com.github.piranha887.library.convertors.AuthorToDtoConverter;
import com.github.piranha887.library.convertors.DtoToAuthorConverter;
import com.github.piranha887.library.domain.Author;
import com.github.piranha887.library.dto.AuthorDto;
import com.github.piranha887.library.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController extends WebController<AuthorDto> {
    public final static String EDIT_FORM = "author-edit";
    public final static String LIST_FORM = "authors-list";
    public final static String REDIRECT = "redirect:/authors";

    private final AuthorRepository repository;
    private final AuthorToDtoConverter toDto;
    private final DtoToAuthorConverter toAuthor;

    @GetMapping
    public String list(Model model) {
        List<Author> authors = repository.findAll();
        model.addAttribute("authors", authors);
        return LIST_FORM;
    }

    @GetMapping("/new")
    public String add(Model model) {
        model.addAttribute("authorDto", new AuthorDto());
        return EDIT_FORM;
    }

    @GetMapping("/edit")
    public String edit(@RequestParam Long id, Model model) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + id));
        model.addAttribute("authorDto", toDto.convert(author));
        return EDIT_FORM;
    }

    @PostMapping("/save")
    public String save(@Valid AuthorDto authorDto,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authorDto", authorDto);
            return EDIT_FORM;
        }

        repository.save(toAuthor.convert(authorDto));
        return REDIRECT;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam long id, Model model) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + id));
        repository.delete(author);
        return REDIRECT;
    }

}

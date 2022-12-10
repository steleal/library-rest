package com.github.piranha887.library.controllers;

import com.github.piranha887.library.convertors.DtoToGenreConverter;
import com.github.piranha887.library.convertors.GenreToDtoConverter;
import com.github.piranha887.library.domain.Genre;
import com.github.piranha887.library.dto.GenreDto;
import com.github.piranha887.library.repositories.GenreRepository;
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
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController extends WebController<GenreDto> {
    private final static String EDIT_FORM = "genre-edit";
    private final static String LIST_FORM = "genres-list";
    private final static String REDIRECT = "redirect:/genres";

    private final GenreRepository repository;
    private final GenreToDtoConverter toDto;
    private final DtoToGenreConverter toGenre;

    @GetMapping
    public String list(Model model) {
        List<GenreDto> genres = repository.findAll().stream()
                .map(toDto::convert)
                .collect(Collectors.toList());
        model.addAttribute("genres", genres);
        return LIST_FORM;
    }

    @GetMapping("/new")
    public String add(Model model) {
        model.addAttribute("genreDto", new GenreDto());
        return EDIT_FORM;
    }

    @GetMapping("/edit")
    public String edit(@RequestParam Long id, Model model) {
        Genre genre = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre Id:" + id));

        model.addAttribute("genreDto", toDto.convert(genre));
        return EDIT_FORM;
    }

    @PostMapping("/save")
    public String save(@Valid GenreDto genreDto,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("genreDto", genreDto);
            return EDIT_FORM;
        }

        repository.save(toGenre.convert(genreDto));
        return REDIRECT;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam long id, Model model) {
        Genre genre = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre Id:" + id));
        repository.delete(genre);
        return REDIRECT;
    }

}

package com.github.piranha887.library.controllers;

import com.github.piranha887.library.convertors.DtoToPublisherConverter;
import com.github.piranha887.library.convertors.PublisherToDtoConverter;
import com.github.piranha887.library.domain.Publisher;
import com.github.piranha887.library.dto.PublisherDto;
import com.github.piranha887.library.repositories.PublisherRepository;
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
@RequestMapping("/publishers")
public class PublisherController extends WebController<PublisherDto> {
    private final static String EDIT_FORM = "publisher-edit";
    private final static String LIST_FORM = "publishers-list";
    private final static String REDIRECT = "redirect:/publishers";

    private final PublisherRepository repository;
    private final PublisherToDtoConverter toDto;
    private final DtoToPublisherConverter toPublisher;

    @GetMapping
    public String list(Model model) {
        List<PublisherDto> publishers = repository.findAll().stream()
                .map(toDto::convert)
                .collect(Collectors.toList());
        model.addAttribute("publishers", publishers);
        return LIST_FORM;
    }

    @GetMapping("/new")
    public String add(Model model) {
        model.addAttribute("publisherDto", new PublisherDto());
        return EDIT_FORM;
    }

    @GetMapping("/edit")
    public String edit(@RequestParam Long id, Model model) {
        Publisher publisher = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid publisher Id:" + id));

        model.addAttribute("publisherDto", toDto.convert(publisher));
        return EDIT_FORM;
    }

    @PostMapping("/save")
    public String save(@Valid PublisherDto publisherDto,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("publisherDto", publisherDto);
            return EDIT_FORM;
        }

        repository.save(toPublisher.convert(publisherDto));
        return REDIRECT;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam long id, Model model) {
        Publisher publisher = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid publisher Id:" + id));
        repository.delete(publisher);
        return REDIRECT;
    }

}

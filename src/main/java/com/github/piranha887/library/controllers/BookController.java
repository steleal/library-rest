package com.github.piranha887.library.controllers;

import com.github.piranha887.library.convertors.AuthorToDtoConverter;
import com.github.piranha887.library.convertors.BookToDtoConverter;
import com.github.piranha887.library.convertors.DtoToBookConverter;
import com.github.piranha887.library.convertors.GenreToDtoConverter;
import com.github.piranha887.library.convertors.PublisherToDtoConverter;
import com.github.piranha887.library.domain.Book;
import com.github.piranha887.library.dto.AuthorDto;
import com.github.piranha887.library.dto.BookDto;
import com.github.piranha887.library.dto.GenreDto;
import com.github.piranha887.library.dto.PublisherDto;
import com.github.piranha887.library.repositories.AuthorRepository;
import com.github.piranha887.library.repositories.BookRepository;
import com.github.piranha887.library.repositories.GenreRepository;
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
@RequestMapping("/books")
public class BookController extends WebController<BookDto> {
    private final static String EDIT_FORM = "book-edit";
    private final static String LIST_FORM = "books-list";
    private final static String REDIRECT = "redirect:/books";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;
    private final BookToDtoConverter toDto;
    private final DtoToBookConverter toBook;
    private final AuthorToDtoConverter authorToDto;
    private final GenreToDtoConverter genreToDto;
    private final PublisherToDtoConverter publisherToDto;

    @GetMapping
    public String list(Model model) {
        List<BookDto> books = bookRepository.findAll().stream()
                .map(toDto::convert)
                .collect(Collectors.toList());
        model.addAttribute("books", books);
        return LIST_FORM;
    }

    @GetMapping("/new")
    public String add(Model model) {
        return showEditForm(new BookDto(), model);
    }

    @GetMapping("/edit")
    public String edit(@RequestParam Long id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));

        return showEditForm(toDto.convert(book), model);
    }

    @PostMapping("/save")
    public String save(@Valid BookDto bookDto,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            return showEditForm(bookDto, model);
        }

        bookRepository.save(toBook.convert(bookDto));
        return REDIRECT;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam long id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        bookRepository.delete(book);
        return REDIRECT;
    }

    private String showEditForm(BookDto bookDto, Model model) {
        model.addAttribute("bookDto", bookDto);
        model.addAttribute("authors", getAuthorList());
        model.addAttribute("genres", getGenreList());
        model.addAttribute("publishers", getPublisherList());
        return EDIT_FORM;
    }

    private List<PublisherDto> getPublisherList() {
        return publisherRepository.findAll().stream()
                .map(publisherToDto::convert)
                .collect(Collectors.toList());
    }

    private List<GenreDto> getGenreList() {
        return genreRepository.findAll().stream()
                .map(genreToDto::convert)
                .collect(Collectors.toList());
    }

    private List<AuthorDto> getAuthorList() {
        return authorRepository.findAll().stream()
                .map(authorToDto::convert)
                .collect(Collectors.toList());
    }

}

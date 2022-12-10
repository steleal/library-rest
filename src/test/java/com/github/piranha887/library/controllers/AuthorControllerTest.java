package com.github.piranha887.library.controllers;

import com.github.piranha887.library.convertors.AuthorToDtoConverter;
import com.github.piranha887.library.convertors.DtoToAuthorConverter;
import com.github.piranha887.library.domain.Author;
import com.github.piranha887.library.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit тест контроллера с использованием Mock.
 * Без использования Spring context.
 */
public class AuthorControllerTest {

    private AuthorRepository mockRepository = mock(AuthorRepository.class);
    private Model model = mock(Model.class);

    private List<Author> authors = Collections.singletonList(new Author(1, "Толстой", null));

    @Test
    public void testReturnAuthorListForm() {
        when(mockRepository.findAll()).thenReturn(authors);
        AuthorController authorController = new AuthorController(mockRepository,
                new AuthorToDtoConverter(), new DtoToAuthorConverter());

        String result = authorController.list(model);

        verify(model, times(1)).addAttribute("authors", authors);
        assertThat(result, is(AuthorController.LIST_FORM));
    }
}
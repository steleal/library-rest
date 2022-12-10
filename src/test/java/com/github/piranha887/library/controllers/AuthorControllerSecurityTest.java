package com.github.piranha887.library.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Интеграционный тест контроллера с использованием Mock.
 * С поднятием Spring context и H2 DB.
 */
@SpringBootTest
class AuthorControllerSecurityTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @WithAnonymousUser
    @Test
    public void getAuthors_shouldRedirectingToLoginForAnon() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/authors").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(
            username = "reader",
            authorities = {"ROLE_READER"}
    )
    @Test
    public void getAuthors_shouldOkForReader() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/authors").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "reader",
            authorities = {"ROLE_READER"}
    )
    @Test
    public void newAuthor_should403StatusForReader() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/authors/new").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(
            username = "editor",
            authorities = {"ROLE_EDITOR"}
    )
    @Test
    public void newAuthors_shouldOkForEditor() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/authors/new").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
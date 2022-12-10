package com.github.piranha887.library.repositories;

import com.github.piranha887.library.domain.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    List<Author> findAll();
}

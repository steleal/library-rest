package com.github.piranha887.library.repositories;

import com.github.piranha887.library.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    @EntityGraph(value = "BookWithDict", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAll();

    @EntityGraph(value = "BookWithDict", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Book> findById(Long id);

    @EntityGraph(value = "BookWithDict", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findBooksByGenre_NameLike(String name);

    @EntityGraph(value = "BookWithDict", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findBooksByAuthor_fullNameLike(String name);
}

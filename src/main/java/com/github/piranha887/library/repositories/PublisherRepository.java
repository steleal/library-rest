package com.github.piranha887.library.repositories;

import com.github.piranha887.library.domain.Publisher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {

    List<Publisher> findAll();
}

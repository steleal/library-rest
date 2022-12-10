package com.github.piranha887.library.convertors;

import com.github.piranha887.library.domain.Publisher;
import com.github.piranha887.library.dto.PublisherDto;
import com.github.piranha887.library.exceptions.NotFoundException;
import com.github.piranha887.library.repositories.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoToPublisherConverter implements Converter<PublisherDto, Publisher> {
    private final PublisherRepository repository;

    @Override
    @NonNull
    public Publisher convert(PublisherDto source) {
        Long id = source.getId();
        Publisher publisher;
        if (id == null || id == 0) {
            publisher = new Publisher();
        } else {
            publisher = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Publisher", id));
        }
        publisher.setName(source.getName());
        return publisher;
    }
}

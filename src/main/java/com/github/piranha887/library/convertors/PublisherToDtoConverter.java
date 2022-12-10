package com.github.piranha887.library.convertors;

import com.github.piranha887.library.domain.Publisher;
import com.github.piranha887.library.dto.PublisherDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PublisherToDtoConverter implements Converter<Publisher, PublisherDto> {
    @Override
    public PublisherDto convert(Publisher source) {
        return new PublisherDto(source.getId(), source.getName());
    }
}

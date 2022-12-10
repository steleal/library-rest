package com.github.piranha887.library.convertors;

import com.github.piranha887.library.domain.Publisher;
import com.github.piranha887.library.dto.PublisherDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToPublisherConverter implements Converter<PublisherDto, Publisher> {
    @Override
    public Publisher convert(PublisherDto source) {
        return new Publisher(source.getId(), source.getName());
    }
}

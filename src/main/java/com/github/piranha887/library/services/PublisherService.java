package com.github.piranha887.library.services;

import com.github.piranha887.library.convertors.DtoToPublisherConverter;
import com.github.piranha887.library.convertors.PublisherToDtoConverter;
import com.github.piranha887.library.domain.Publisher;
import com.github.piranha887.library.dto.PublisherDto;
import com.github.piranha887.library.exceptions.NotFoundException;
import com.github.piranha887.library.repositories.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherRepository repository;
    private final PublisherToDtoConverter toDto;
    private final DtoToPublisherConverter toPublisher;

    @Transactional(readOnly = true)
    public List<PublisherDto> findAll() {
        return repository.findAll().stream()
                .map(toDto::convert)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PublisherDto getById(long publisherId) {
        return repository.findById(publisherId)
                .map(toDto::convert)
                .orElseThrow(() -> new NotFoundException("Publisher", publisherId));
    }

    public long add(PublisherDto publisherDto) {
        Publisher save = repository.save(toPublisher.convert(publisherDto));
        return save.getId();
    }

    public void update(long publisherId, PublisherDto publisherDto) {
        publisherDto.setId(publisherId);
        repository.save(toPublisher.convert(publisherDto));
    }

    public void delete(long publisherId) {
        if (repository.existsById(publisherId)) {
            repository.deleteById(publisherId);
        } else {
            throw new NotFoundException("Publisher", publisherId);
        }
    }

}

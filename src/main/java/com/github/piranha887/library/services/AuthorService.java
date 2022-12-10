package com.github.piranha887.library.services;

import com.github.piranha887.library.convertors.AuthorToDtoConverter;
import com.github.piranha887.library.convertors.DtoToAuthorConverter;
import com.github.piranha887.library.domain.Author;
import com.github.piranha887.library.dto.AuthorDto;
import com.github.piranha887.library.exceptions.NotFoundException;
import com.github.piranha887.library.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;
    private final AuthorToDtoConverter toDto;
    private final DtoToAuthorConverter toAuthor;

    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return repository.findAll().stream()
                .map(toDto::convert)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AuthorDto getById(long authorId) {
        return repository.findById(authorId)
                .map(toDto::convert)
                .orElseThrow(() -> new NotFoundException("Author", authorId));
    }

    public long add(AuthorDto authorDto) {
        Author save = repository.save(toAuthor.convert(authorDto));
        return save.getId();
    }

    public void update(long authorId, AuthorDto authorDto) {
        authorDto.setId(authorId);
        repository.save(toAuthor.convert(authorDto));
    }

    public void delete(long authorId) {
        if (repository.existsById(authorId)) {
            repository.deleteById(authorId);
        } else {
            throw new NotFoundException("Author", authorId);
        }
    }

}

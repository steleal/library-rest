package com.github.piranha887.library.services;

import com.github.piranha887.library.convertors.AuthorToDtoConverter;
import com.github.piranha887.library.convertors.DtoToAuthorConverter;
import com.github.piranha887.library.domain.Author;
import com.github.piranha887.library.dto.AuthorDto;
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
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    public long add(AuthorDto authorDto) {
        Author author = toAuthor.convert(authorDto);
        author.setId(0);
        Author save = repository.save(author);
        return save.getId();
    }

    public void update(long authorId, AuthorDto authorDto) {
        Author author = repository.findById(authorId).orElseThrow(() -> new RuntimeException("Author not found"));
        updateFields(author, authorDto);
        repository.save(author);
    }

    public void delete(long authorId) {
        if (repository.existsById(authorId)) {
            repository.deleteById(authorId);
        } else {
            throw new RuntimeException("Author not found");
        }
    }

    private void updateFields(Author author, AuthorDto authorDto) {
        if (authorDto != null) {
            String fullName = authorDto.getFullName();
            if (fullName != null) {
                author.setFullName(fullName);
            }
            Integer birthday = authorDto.getBirthday();
            if (birthday != null) {
                author.setBirthday(birthday);
            }
        }
    }
}

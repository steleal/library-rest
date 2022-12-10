package com.github.piranha887.library.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"),
    READER("ROLE_READER"),
    WRITER("ROLE_EDITOR");

    private final String role;

    public String toString(){
        return role;
    }
}

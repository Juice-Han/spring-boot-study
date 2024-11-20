package org.example.testsecurity.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AdminDeleteUserResponse {
    private Long id;

    @Builder
    public AdminDeleteUserResponse(Long id) {
        this.id = id;
    }
}

package org.example.testsecurity.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AdminDeleteUserResponse {
    private int id;

    @Builder
    public AdminDeleteUserResponse(int id) {
        this.id = id;
    }
}

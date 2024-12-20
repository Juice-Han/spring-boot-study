package org.example.testsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.example.testsecurity.dto.AdminDeleteUserResponse;
import org.example.testsecurity.service.ArticleService;
import org.example.testsecurity.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminRestController {
    private final UserService userService;
    private final ArticleService articleService;

    @DeleteMapping("/api/admin/users/{id}")
    public ResponseEntity<AdminDeleteUserResponse> deleteUser(@PathVariable("id") Long id){
        userService.deleteUserById(id);
        return new ResponseEntity<>(AdminDeleteUserResponse.builder()
                .id(id)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id){
        articleService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

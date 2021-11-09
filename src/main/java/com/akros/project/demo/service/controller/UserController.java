package com.akros.project.demo.service.controller;

import com.akros.project.demo.service.schema.CatDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/cat")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserClient userClient;

    @GetMapping("/getAllCats")
    public List<CatDTO> getAllCats() {
        return userClient.getAllCats();
    }

    @GetMapping("/{id}")
    public CatDTO getCat(@PathVariable("id") String id) {
        return userClient.getCatById(id);
    }

}

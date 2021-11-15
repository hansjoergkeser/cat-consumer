package com.akros.project.demo.service.controller;

import com.akros.project.demo.service.schema.CatDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/cat")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserClient userClient;

    @GetMapping("/getAllCats")
    public List<CatDTO> getAllCats() {
        return userClient.getAllCats();
    }

    @GetMapping("/get/{catName}")
    public CatDTO getCat(@PathVariable("catName") String catName) {
        return userClient.getCatByName(catName);
    }

    @PostMapping("/createCat")
    public CatDTO createCat(@RequestBody RequestEntity<CatDTO> data) {
        return userClient.createCat(data.getBody());
    }

}

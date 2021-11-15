package com.akros.project.demo.service;

import com.akros.project.demo.service.controller.UserClient;
import com.akros.project.demo.service.schema.CatDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("local/manual testing")
@SpringBootTest
public class CatApiTests {

    @Autowired
    UserClient userClient;

    @Test
    void testCreateCat() {
        userClient.createCat(
                new CatDTO(
                        null,
                        "Hajo-gato",
                        "schwarzo",
                        "schnurro",
                        "malo",
                        666
                )
        );
    }

}

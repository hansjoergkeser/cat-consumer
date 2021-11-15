package com.akros.project.demo.service.integration;

import com.akros.project.demo.service.controller.UserClient;
import com.akros.project.demo.service.schema.CatDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Disabled("local/manual testing")
@SpringBootTest
public class CatApiTests {

    @Autowired
    UserClient userClient;

    @Test
    void testCreateCat() {
        String expectedName = "Hajo-gato";

        CatDTO responseDTO = userClient.createCat(
                new CatDTO(
                        null,
                        expectedName,
                        "schwarzo",
                        "schnurro",
                        "malo",
                        666
                )
        );

        assertNotNull(responseDTO, "Should have received response body with cat dto created by provider service");
        assertTrue(responseDTO.getId() > Long.MIN_VALUE, "Created cat should have received an auto generated id.");
        assertEquals(expectedName, responseDTO.getName(), "Name was not saved/created correctly");
    }

}

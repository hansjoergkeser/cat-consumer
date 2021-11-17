package com.akros.project.demo.service.contracts;

import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.akros.project.demo.service.controller.UserClient;
import com.akros.project.demo.service.controller.UserController;
import com.akros.project.demo.service.schema.CatDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {UserController.class, UserClient.class})
@AutoConfigureWebClient
@PactTestFor(port = "8083")
@TestPropertySource(properties = {
        // overrides default spring property in application.properties
        // to avoid conflict with the "real" provider service on localhost http://localhost:8080/cats
        // Not that /cats is application context, not part of the path
        "user.service.url=http://localhost:8083"
        // enable if test fails to get more details in log
//        ,"logging.level.au.com.dius.pact=DEBUG"
})
@ExtendWith(PactConsumerTestExt.class)
public class CatControllerConsumerPactTest {

    private static final long EXAMPLE_ID = 101;
    private static final String EXAMPLE_NAME = "Catman";
    private static final String EXAMPLE_COLOR = "Blackman";
    private static final String EXAMPLE_CHARACTER = "Pactman";
    private static final String EXAMPLE_GENDER = "Maleman";
    private static final int EXAMPLE_PRICE = 911;

    @Autowired
    private UserController userController;

    @Autowired
    private UserClient userClient;

    private CatDTO createCatDTO() {
        return new CatDTO(
                EXAMPLE_ID,
                EXAMPLE_NAME,
                EXAMPLE_COLOR,
                EXAMPLE_CHARACTER,
                EXAMPLE_GENDER,
                EXAMPLE_PRICE
        );
    }

    @Pact(provider = "PactCatProvider", consumer = "PactCatConsumer")
    public RequestResponsePact createPact_getAllCats(PactDslWithProvider builder) {
        return builder
                .uponReceiving("/getCats response from provider service")
                .path("/getCats")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(
                        // https://docs.pact.io/implementation_guides/jvm/consumer
                        PactDslJsonArray.arrayEachLike()
                                .integerType("id", EXAMPLE_ID)
                                .stringType("name", EXAMPLE_NAME)
                                .stringType("color", EXAMPLE_COLOR)
                                .stringType("character", EXAMPLE_CHARACTER)
                                .stringType("gender", EXAMPLE_GENDER)
                                .integerType("price", EXAMPLE_PRICE)
                )
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createPact_getAllCats")
    public void testCreatePact_getAllCats() {
        List<CatDTO> responseOfMockServer = assertDoesNotThrow(() -> userController.getAllCats());
        assertFalse(responseOfMockServer.isEmpty(), "Response must not be empty, " +
                "should contain list with the example cat dto, as defined above.");
        assertEquals(Collections.singletonList(createCatDTO()), responseOfMockServer,
                "Response body contained a different cat dto as expected.");
    }

}

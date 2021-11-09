package com.akros.project.demo.service.controller;

import com.akros.project.demo.service.schema.CatDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Slf4j
@Service
public class UserClient {

    private final WebClient webClient;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public UserClient(WebClient.Builder webClientBuilder,
                      @Value("${user.service.url}") String userServiceUrl) {
        this.webClient = webClientBuilder
                .baseUrl(userServiceUrl)
                // only needed for IntelliJ
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
                .filter(logRequest())
                .build();
    }

    // This method returns filter function which will log request data
    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Outgoing request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }

    public List<CatDTO> getAllCats() {
        try {
            CatDTO[] userArray = webClient.get().uri("/getCats").retrieve().bodyToMono(CatDTO[].class).block();

            if (userArray != null) {
                return asList(userArray);
            }
        } catch (Exception e) {
            log.error("getAllUsers failed: " + e);
        }
        return emptyList();
    }

//    public CatDTO getCatById(String id) {
//        return webClient.get().
//                uri("/{id}", id).
//                retrieve().
//                bodyToMono(CatDTO.class).
//                onErrorMap(WebClientResponseException.BadRequest.class,
//                        e -> new Exception("BadRequest on user API.", e)).
//                block();
//    }

}

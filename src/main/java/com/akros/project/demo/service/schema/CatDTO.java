package com.akros.project.demo.service.schema;

import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class CatDTO {

    Long id;

    @NonNull
    String name;

    @NotNull
    String color;

    @NonNull
    String character;

    @NonNull
    String gender;

    int price;

}

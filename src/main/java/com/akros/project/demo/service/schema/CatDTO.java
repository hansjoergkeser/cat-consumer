package com.akros.project.demo.service.schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Note: The consumer is not interested in all data of the provider's response.
 * The consumer client does e.g. not need/parse color or character.
 * Therefore, when specifying pacts for the provider, this consumer will completely ignore those additional fields
 */
@Data
@NoArgsConstructor
public class CatDTO {

    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String gender;

    @NonNull
    private String price;

}

package com.clothing.feedback.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "favorite_product")
public class FavoriteProduct {

    @Id
    private UUID id;
    private Integer productId;
    private String userId;
}

package com.clothing.customer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FavoriteProduct {

    private UUID id;
    private Integer productId;
}

package com.clothing.manager.controllers.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProductPayload(
        @NotNull(message = "{atum.products.update.errors.title_is_null}")
        @Size(min = 3, max = 50, message = "{atum.products.update.errors.title_size_is_invalid}")
        String title,

        @Size(max = 1000, message = "{atum.products.update.errors.description_size_is_invalid}")
        String description
) {
}

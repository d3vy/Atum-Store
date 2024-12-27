package com.clothing.customer.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ClientBadRequestException extends RuntimeException {

    private final List<String> errors;

    public ClientBadRequestException(Throwable cause, List<String> errors) {
        super(cause);
        this.errors = errors;
    }
}

package com.example.demo.catalog.domain;

import jakarta.persistence.Embeddable;

public record Isbn(String value) {

    // TODO can be added validation logic here
    public Isbn {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
    }

}

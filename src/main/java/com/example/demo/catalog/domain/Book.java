package com.example.demo.catalog.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

@Getter
@Setter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "isbn"))
    private Isbn isbn;

    private int copies;

    /**
     * Validates the stock of the book. Throws an exception if the stock is invalid.
     */
    public void validateStock() {
        Assert.notNull(title, "Title cannot be null");
        Assert.hasText(title, "Title cannot be empty");
        Assert.isTrue(copies >= 1, "Must have at least one copy");
    }

    /**
     * Reserves a copy of the book. Return true if the copy was reserved, false otherwise.
     * @return
     */
    public boolean reserveCopy() {
        if(copies > 0) {
            copies--;
            return true;
        }
        return false;
    }

}

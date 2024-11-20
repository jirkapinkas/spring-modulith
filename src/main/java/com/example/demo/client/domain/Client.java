package com.example.demo.client.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String email;

    @OneToMany(mappedBy = "client", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<WantedBook> wantedBooks;

    public boolean containsWantedBook(String title) {
        return wantedBooks.stream()
                .anyMatch(wantedBook -> title.equals(wantedBook.getTitle()));
    }

    public void removeWantedBook(String title) {
        wantedBooks.removeIf(wantedBook -> wantedBook.getTitle().equals(title));
    }

}

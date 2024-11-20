package com.example.demo.catalog.repository;

import com.example.demo.catalog.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}

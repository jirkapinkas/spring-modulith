package com.example.demo.client.repository;

import com.example.demo.client.domain.WantedBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WantedBookRepository extends JpaRepository<WantedBook, String> {
}

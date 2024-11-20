package com.example.demo.catalog.controller;

import com.example.demo.catalog.dto.BookDto;
import com.example.demo.catalog.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/catalog")
@RequiredArgsConstructor
@RestController
public class CatalogController {

    private final BookService bookService;

    @PostMapping
    public void stockBook(@RequestBody BookDto bookDto) {
        bookService.stock(bookDto);
    }

    @GetMapping
    public List<BookDto> getBooks() {
        return bookService.getBooks();
    }

}

package com.example.demo.catalog.event;

import com.example.demo.catalog.dto.BookDto;

public record BookReservedEvent(String bookId, String clientId, BookDto bookDto) {
}

package com.example.demo.catalog.event;

import com.example.demo.catalog.dto.BookDto;

public record BookStockedEvent(String id, BookDto bookDto) {
}

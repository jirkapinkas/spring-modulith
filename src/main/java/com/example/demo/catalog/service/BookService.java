package com.example.demo.catalog.service;

import com.example.demo.catalog.domain.Book;
import com.example.demo.catalog.dto.BookDto;
import com.example.demo.catalog.event.BookStockedEvent;
import com.example.demo.catalog.mapper.BookMapper;
import com.example.demo.catalog.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final ApplicationEventPublisher events;

    private final BookMapper bookMapper;

    @Transactional
    public void stock(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        book.validateStock();
        Book savedBook = bookRepository.save(book);
        events.publishEvent(new BookStockedEvent(savedBook.getId(), bookDto));
    }

    public List<BookDto> getBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

}

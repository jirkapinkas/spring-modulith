package com.example.demo.catalog.handler;

import com.example.demo.catalog.dto.BookDto;
import com.example.demo.catalog.event.BookReservedEvent;
import com.example.demo.catalog.mapper.BookMapper;
import com.example.demo.catalog.repository.BookRepository;
import com.example.demo.client.event.ReserveCopyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatalogEventHandler {

    private final BookRepository bookRepository;

    private final ApplicationEventPublisher events;

    private final BookMapper bookMapper;

    @EventListener
    public void handleEvent(ReserveCopyEvent reserveCopyEvent) {
        log.info("Received event: {}", reserveCopyEvent);
        bookRepository.findById(reserveCopyEvent.bookId())
                .ifPresent(book -> {
                    boolean reserved = book.reserveCopy();
                    if (reserved) {
                        bookRepository.save(book);
                        log.info("Reserved copy of book: {}", reserveCopyEvent.bookId());
                        BookDto bookDto = bookMapper.toDto(book);
                        events.publishEvent(new BookReservedEvent(reserveCopyEvent.bookId(), reserveCopyEvent.clientId(), bookDto));
                    } else {
                        log.info("Could not reserve copy of a book: {}", reserveCopyEvent.bookId());
                    }
                });
    }

}

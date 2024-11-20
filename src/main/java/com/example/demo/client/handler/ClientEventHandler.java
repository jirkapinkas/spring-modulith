package com.example.demo.client.handler;

import com.example.demo.catalog.event.BookStockedEvent;
import com.example.demo.catalog.event.BookReservedEvent;
import com.example.demo.client.domain.Client;
import com.example.demo.client.event.ReserveCopyEvent;
import com.example.demo.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientEventHandler {

    private final ApplicationEventPublisher events;

    private final ClientRepository clientRepository;

//    @Async
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    @EventListener
    public void handleBookCreatedEvent(BookStockedEvent bookStockedEvent) {
        log.info("Received event: {}", bookStockedEvent);
        List<Client> clients = clientRepository.findAll();
        clients.forEach(client -> {
            if (client.containsWantedBook(bookStockedEvent.bookDto().getTitle())) {
                log.info("Reserve copy of book: {} for client: {}", bookStockedEvent.id(), client.getId());
                events.publishEvent(new ReserveCopyEvent(bookStockedEvent.id(), client.getId()));
            }
        });
    }

//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    @EventListener
    public void handleBookReservedEvent(BookReservedEvent bookReservedEvent) {
        log.info("Received event: {}", bookReservedEvent);
        clientRepository.findById(bookReservedEvent.clientId())
                .ifPresent(client -> {
                    client.removeWantedBook(bookReservedEvent.bookDto().getTitle());
                    clientRepository.save(client);
                });
        // TODO call notification module & send email
        throw new RuntimeException();
    }

}

package com.example.demo;

import com.example.demo.client.domain.Client;
import com.example.demo.client.domain.WantedBook;
import com.example.demo.client.repository.ClientRepository;
import com.example.demo.client.repository.WantedBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class LibraryApplication {

	private final ClientRepository clientRepository;

	private final WantedBookRepository wantedBookRepository;

	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {
		Client client = new Client();
		client.setName("John Doe");
		Client savedClient = clientRepository.save(client);

		WantedBook wantedBook = new WantedBook();
		wantedBook.setClient(savedClient);
		wantedBook.setTitle("The Lord of the Rings");
		wantedBookRepository.save(wantedBook);
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

}

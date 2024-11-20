package com.example.demo.catalog.mapper;

import com.example.demo.catalog.domain.Book;
import com.example.demo.catalog.dto.BookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Book book);
    Book toEntity(BookDto bookDto);
}

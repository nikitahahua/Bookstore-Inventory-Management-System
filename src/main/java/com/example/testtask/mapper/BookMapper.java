package com.example.testtask.mapper;

import com.example.testtask.dto.BookDTO;
import com.example.testtask.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BookMapper {

    @Mapping(source = "title", target = "title")
    BookDTO bookToDto(Book book);

    @Mapping(source = "title", target = "title")
    Book dtoToBook(BookDTO bookDTO);

    Book bookFromProtoToBook(com.example.grps.BookstoreService.Book book);

    BookDTO protoBookToDto(com.example.grps.BookstoreService.Book book);
}

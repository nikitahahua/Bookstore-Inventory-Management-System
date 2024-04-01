package com.example.testtask.service;

import com.example.testtask.dto.BookDTO;
import com.example.testtask.model.Book;

import java.util.List;

public interface BookService {
    Book createBook(BookDTO bookDTO);
    Book updateBook(BookDTO bookDTO);
    void deleteBook(String bookName);
    Book getBookByTitle(String title);
    List<Book> getAllBooksByAuthor(String authorName);
}

package com.example.testtask.repository;

import com.example.testtask.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findBookByAuthor (String author);
    Book findBookByTitle (String author);
}

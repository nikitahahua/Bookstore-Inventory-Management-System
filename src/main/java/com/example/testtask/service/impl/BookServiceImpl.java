package com.example.testtask.service.impl;

import com.example.testtask.exceptions.BookNotFoundException;
import com.example.testtask.exceptions.InvalidInputException;
import com.example.testtask.dto.BookDTO;
import com.example.testtask.model.Book;
import com.example.testtask.repository.BookRepository;
import com.example.testtask.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book createBook(BookDTO bookDTO) {
        validateBookAttributes(bookDTO);

        return repository.save(new Book(bookDTO.title(),
                bookDTO.author(),
                bookDTO.isbn(),
                bookDTO.quantity()));
    }

    @Override
    public Book updateBook(BookDTO bookDTO) {
        if (bookDTO == null){
            throw new InvalidInputException("invalid book dto was provided");
        }
        validateBookAttributes(bookDTO);
        Book bookToUpdate = getBookByTitle(bookDTO.title());

        if (bookToUpdate == null) {
            throw new BookNotFoundException("book by title : " + bookDTO.title() + " wasn't found");
        }

        bookToUpdate.setAuthor(bookDTO.author());
        bookToUpdate.setIsbn(bookDTO.isbn());
        bookToUpdate.setQuantity(bookDTO.quantity());

        return repository.save(bookToUpdate);
    }

    @Override
    public void deleteBook(String title) {
        repository.delete(getBookByTitle(title));
    }

    @Override
    public Book getBookByTitle(String title) {
        if (title.isEmpty()) {
            throw new InvalidInputException("invalid title was provided");
        }
        return repository.findBookByTitle(title);
    }

    @Override
    public List<Book> getAllBooksByAuthor(String authorName) {
        if (authorName.isEmpty()){
            throw new InvalidInputException("invalid authors name was provided");
        }
        return repository.findBookByAuthor(authorName);
    }

    private void validateBookAttributes(BookDTO bookDTO) {
        if (bookDTO.title().isEmpty() ||
                bookDTO.author().isEmpty() ||
                bookDTO.isbn().isEmpty() ||
                bookDTO.quantity() < 1) {
            throw new InvalidInputException("Invalid parameters were provided");
        }
    }
}

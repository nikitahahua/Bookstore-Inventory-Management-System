package com.example.testtask.service.impl;

import com.example.grps.BookServiceGrpc;
import com.example.grps.BookstoreService;
import com.example.testtask.mapper.BookMapper;
import com.example.testtask.model.Book;
import com.example.testtask.service.BookService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@Slf4j
public class BookstoreServiceImpl extends BookServiceGrpc.BookServiceImplBase {

    private final BookService bookService;
    private final BookMapper bookMapper;
    public BookstoreServiceImpl(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @Override
    public void getBook(BookstoreService.GetBookRequest request, StreamObserver<BookstoreService.GetBookResponse> responseObserver) {

        Book bookFromDb = bookService.getBookByTitle(request.getTitle());
        log.info("read book from db");
        BookstoreService.GetBookResponse response = BookstoreService.GetBookResponse.newBuilder()
                .setBook(BookstoreService.Book.newBuilder()
                        .setTitle(bookFromDb.getTitle())
                        .setAuthor(bookFromDb.getAuthor())
                        .setIsbn(bookFromDb.getIsbn())
                        .setQuantity(bookFromDb.getQuantity())
                        .build())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void updateBook(BookstoreService.UpdateBookRequest request, StreamObserver<BookstoreService.UpdateBookResponse> responseObserver) {
        bookService.updateBook(bookMapper.protoBookToDto(request.getBookToUpdate()));
        log.info("updated book by name: " + request.getBookToUpdate().getTitle());
    }

    @Override
    public void deleteBook(BookstoreService.DeleteBookRequest request, StreamObserver<BookstoreService.DeleteBookResponse> responseObserver) {
        bookService.deleteBook(request.getTitle());
        log.info("deleted book from db");
    }

    @Override
    public void getAllBooksByAuthor(BookstoreService.GetAllBooksByAuthorRequest request, StreamObserver<BookstoreService.GetAllBooksByAuthorResponse> responseObserver) {
        List<Book> books = bookService.getAllBooksByAuthor(request.getAuthorsName());

        BookstoreService.GetAllBooksByAuthorResponse.Builder responseBuilder = BookstoreService.GetAllBooksByAuthorResponse.newBuilder();

        responseBuilder.addAllBook(books.stream()
                .map(book -> BookstoreService.Book.newBuilder()
                        .setTitle(book.getTitle())
                        .setAuthor(book.getAuthor())
                        .setIsbn(book.getIsbn())
                        .setQuantity(book.getQuantity())
                        .build())
                .collect(Collectors.toList()));

        log.info("got all books by authors name: " + request.getAuthorsName());

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}

package com.example.testtask.dto;

import jakarta.persistence.Column;
import lombok.Builder;

@Builder
public record BookDTO (
    String title,
    String author,
    String isbn,
    Integer quantity
){}

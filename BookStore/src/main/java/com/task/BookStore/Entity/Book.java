package com.task.BookStore.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Entity
public class Book {
    private String bookName;
    private String discription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
}

package com.task.BookStore.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Book {
    @Id@GeneratedValue(strategy = GenerationType.SEQUENCE) private Long bookId;
    private String bookName;

    @ManyToOne
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }


}

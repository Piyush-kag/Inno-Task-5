package com.task.BookStore.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Author {
    @Id@GeneratedValue(strategy = GenerationType.SEQUENCE) private Long authorId;
    private String authorName;

    @OneToMany(mappedBy="author")
    private List<Book> bookList;

    public List<Book> getBookList() {
        return bookList;
    }
    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public Long getId() {
        return authorId;
    }

    public void setId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}

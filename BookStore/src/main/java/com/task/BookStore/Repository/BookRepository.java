package com.task.BookStore.Repository;

import com.task.BookStore.Entity.Book;
import com.task.BookStore.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}

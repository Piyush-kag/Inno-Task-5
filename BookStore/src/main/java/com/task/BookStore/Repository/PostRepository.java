package com.task.BookStore.Repository;

import com.task.BookStore.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Author, Long> {
}

package com.task.BookStore.Repository;

import com.task.BookStore.Entity.Book;
import com.task.BookStore.Entity.Role;
import com.task.BookStore.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    User findByRole(Role role);
}

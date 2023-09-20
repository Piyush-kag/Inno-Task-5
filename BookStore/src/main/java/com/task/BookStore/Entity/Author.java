package com.task.BookStore.Entity;
import com.task.BookStore.Repository.UserRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Author {
    private String authorName;
    private LocalDate dob;
    private int publishedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

}



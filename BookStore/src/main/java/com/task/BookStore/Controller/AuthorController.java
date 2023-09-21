package com.task.BookStore.Controller;
import com.oracle.svm.core.annotate.Delete;
import com.task.BookStore.Entity.Author;
import com.task.BookStore.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorRepository  authorRepository;

    @GetMapping("/")
   public List<Author> get()
    {
        return authorRepository.findAll();
    }

    @PostMapping("/save")
    public Author save(@RequestBody Author author){
       return authorRepository.save(author);
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.get();
    }
        @DeleteMapping("/{id}")
        public boolean deletById(@PathVariable Long id){
            try{
                authorRepository.deleteById(id);
            }
            catch(Exception e){
                System.out.println(e);
            }
            return true;
        }
}
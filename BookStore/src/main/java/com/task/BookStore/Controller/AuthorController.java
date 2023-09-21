package com.task.BookStore.Controller;
import com.task.BookStore.Entity.Author;
import com.task.BookStore.Entity.Book;
import com.task.BookStore.Repository.AuthorRepository;
import com.task.BookStore.Repository.BookRepository;
import jakarta.annotation.Nullable;
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
//Author

    @GetMapping("/")
   public List<Author> getList()
    {
        return authorRepository.findAll();
    }

    @PostMapping("/saveAuthor")
    public Author save(@RequestBody Author author){
       return authorRepository.save(author);
    }

    //Author
    @GetMapping("/{id}")
    public Author getById(@PathVariable Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.get();
    }

    @DeleteMapping("/{id}")
    public String deletById(@PathVariable Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        try {
            if (authorOptional.isPresent()) {
                Author author = authorOptional.get();
                authorRepository.delete(author);
                return "Author deleted successfully";
            }
            else {return "Id not Found";}
        }
        catch (Exception e){
            System.out.println(e);
        }
        return "Nothing to Delete";
    }
       @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author updatedAuthor) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()) {
            Author existingAuthor = authorOptional.get();

            existingAuthor.setAuthorName(updatedAuthor.getAuthorName());
            // Save the updated entity
            Author savedAuthor = authorRepository.save(existingAuthor);

            return ResponseEntity.ok(savedAuthor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //BOOK

    @Autowired
    private BookRepository bookRepository;

    public void BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/{id}/b")
    public List<Book> getBook(@PathVariable Long id)
    {
        Optional<Author> findBookByAuthor = authorRepository.findById(id);
        Author author=findBookByAuthor.get();
        return author.getBookList();
    }

    //book
    @DeleteMapping("/author/db{id}")
    public String deleteBookById(@PathVariable Long id ){
        Optional<Book> bookOptional = bookRepository.findById(id);
        try {
            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                bookRepository.delete(book);
                return "Book Deleted Successfully";
            }
            else {
                return "Book Id Not Found";
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return "Nothing to Delete";
    }

    //Book
    @PostMapping("/sb")
    public Book save(@RequestBody Book book){
        return bookRepository.save(book);
    }

    @GetMapping("/book{id}")
    public Book getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.get();
    }

    @PutMapping("/ub{id}")
    public ResponseEntity<Book> updateAuthor(@PathVariable Long id, @RequestBody Book updatedBook) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isPresent()) {
            Book existingBook = bookOptional.get();

            existingBook.setBookName(updatedBook.getBookName());

            Book savedBook = bookRepository.save(existingBook);

            return ResponseEntity.ok(savedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
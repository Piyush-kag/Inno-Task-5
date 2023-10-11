package com.task.BookStore.Controller;

import com.task.BookStore.Entity.Author;
import com.task.BookStore.Entity.Book;
import com.task.BookStore.Repository.AuthorRepository;
import com.task.BookStore.Repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    public void BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //Author

    //Get All authorss
    @GetMapping("/")
    public List<Author> getList() {

        return authorRepository.findAll();
    }

    //Add Author
    @PostMapping("/")
    @Validated
    public ResponseEntity<Author> saveAuthorName(@Valid @RequestBody String authorName) {
        Author newAuthor = new Author();
        newAuthor.setAuthorName(authorName);

        Author savedAuthor = authorRepository.save(newAuthor);
        return ResponseEntity.ok(savedAuthor);
    }

    //Author by ID
    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            return ResponseEntity.ok(author);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Author delete by id
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return "Author deleted successfully";
        }
        return "Author not found";
    }

    //Update Author
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author updatedAuthor) {
        System.out.println("Author ID" + id);
        System.out.println("Author Name" + updatedAuthor);
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Author> existingAuthorOptional = authorRepository.findById(id);
        if (existingAuthorOptional.isPresent()) {
            Author existingAuthor = existingAuthorOptional.get();
            existingAuthor.setAuthorName(updatedAuthor.getAuthorName());
            Author savedAuthor = authorRepository.save(existingAuthor);
            return ResponseEntity.ok(savedAuthor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //BOOK

    //get All book
    @GetMapping("/books")
    public List<Book> getBookById() {
        return bookRepository.findAll();
    }


    //add book
    @PostMapping("{id}/books")
    public ResponseEntity<Author> save(@PathVariable Long id, @RequestBody Book book) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            book.setAuthor(author);
            bookRepository.save(book);
            return ResponseEntity.ok(author); // Return the updated author
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<List<Book>> getBookList(@PathVariable Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            return ResponseEntity.ok(author.getBookList());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //particular book from particular author
    @GetMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<List<Book>> getBookFromAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (authorOptional.isPresent() && bookOptional.isPresent()) {
            Author author = authorOptional.get();
            Book book = bookOptional.get();
            // Check if the book belongs to the specified author
            if (author.getBookList().contains(book)) {
                List<Book> books = new ArrayList<>();
                books.add(book);
                return ResponseEntity.ok(books);
            }
        }
        // Return an empty list if the author or book relationship is not found
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book updatedBook) {
        System.out.println("Book:" +bookId +"BookName:" +updatedBook);
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isPresent()) {
            Book existingBook = bookOptional.get();
            existingBook.setBookName(updatedBook.getBookName());
                Book savedBook = bookRepository.save(existingBook);
            return ResponseEntity.ok(savedBook);
        }
        return ResponseEntity.notFound().build();
    }

    //delete book from its ID
    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId) {
        System.out.println(bookId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            // Delete the book
            bookRepository.delete(book);
            return ResponseEntity.ok("Book Deleted");
        }

        return ResponseEntity.notFound().build();
    }

}
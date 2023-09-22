package com.task.BookStore.Controller;

import com.task.BookStore.Entity.Author;
import com.task.BookStore.Entity.Book;
import com.task.BookStore.Repository.AuthorRepository;
import com.task.BookStore.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;
//Author

    @GetMapping("/")
    public List<Author> getList() {
        return authorRepository.findAll();
    }

    @PostMapping("/saveAuthor")
    public Author save(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    //Author
    @GetMapping("/{id}")
    public Author getById(@PathVariable Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.get();
    }
//Author delete by id
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
            if (authorRepository.existsById(id)) {
                authorRepository.deleteById(id);
                return "Author deleted successfully";
            } else {
                return "Author not found";
            }
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

    @GetMapping("/books")
    public List<Book> getBookById() {
        return bookRepository.findAll();
    }

//    //book
//    @DeleteMapping("/db/{id}")
//    public String deleteBookById(@PathVariable Long id) {
//        Optional<Book> bookOptional = bookRepository.findById(id);
//        try {
//            if (bookOptional.isPresent()) {
//                Book book = bookOptional.get();
//                bookRepository.delete(book);
//                return "Book Deleted Successfully";
//            } else {
//                return "Book Id Not Found";
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return "Nothing to Delete";
//    }

    //addBook
    @PostMapping("/{id}/saveBook")
    public ResponseEntity<Object> save(@PathVariable Long id, @RequestBody Book book) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            book.setAuthor(author);
            bookRepository.save(book);
            return ResponseEntity.ok("Book saved for author with ID " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //all book  of that particular author
    @GetMapping("/{id}/books")
    public List<Book> getBookList(@PathVariable Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            List<Book> books = author.getBookList();
            return books;
        } else {
            return new ArrayList<>(); // Return an empty list when author is not found
        }
    }

    //particular book from particular author
    @GetMapping("/{authorId}/book/{bookId}")
    public List<Book> getBookFromAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);

        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            Optional<Book> bookOptional = bookRepository.findById(bookId);

            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();

                // Check if the book belongs to the specified author
                if (author.getBookList().contains(book)) {
                    List<Book> books = new ArrayList<>();
                    books.add(book);
                    return books;
                }
            }
        }
        // Return a Empty List if Author and book reltn not found
        return new ArrayList<>();
    }

    @PutMapping("/{authorId}/updateBook/{bookId}")
    public ResponseEntity<Book> updateAuthor(@PathVariable Long authorId,
                                             @PathVariable Long bookId,
                                             @RequestBody Book updatedBook
    ) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);

        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();

            // Find the book within the author's book list by bookId
            Optional<Book> bookOptional = author.getBookList().stream()
                    .filter(book -> book.getBookId().equals(bookId))
                    .findFirst();

            if (bookOptional.isPresent()) {
                Book existingBook = bookOptional.get();

                // Update the book name
                existingBook.setBookName(updatedBook.getBookName());

                // Save the updated book
                Book savedBook = bookRepository.save(existingBook);

                return ResponseEntity.ok(savedBook);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //particular book from particular author
    @DeleteMapping("/{authorId}/deleteBook/{bookId}")
    public String deleteBookFromAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);

        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            Optional<Book> bookOptional = bookRepository.findById(bookId);

            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();

                // Check if the book belongs to the specified author
                if (author.getBookList().contains(book)) {
                    bookRepository.delete(book);
                    return "Book Deleted from Author";
                }
            }
        }
        // Return no reltn found
        return "No Relation Found in Book and Author";
    }
}
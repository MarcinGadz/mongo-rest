package com.nbd.demo.api;

import com.nbd.demo.LoadStartupData;
import com.nbd.demo.dao.BookRepository;
import com.nbd.demo.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookRepository bookRepo;

    @Autowired
    LoadStartupData helper;

    @DeleteMapping("/")
    public ResponseEntity<String> deleteAll() {
        bookRepo.deleteAll();
        helper.addBooks();
        return ResponseEntity.ok("Reloaded all books");
    }

    @GetMapping("/")
    public ResponseEntity<List<Book>> findAll() {
        List<Book> books = bookRepo.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findByID(@PathVariable String id) {
        //Returns user with specified id or null if such user doesn't exist
        Book b = bookRepo.findById(id).orElse(null);
        if (b == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(b, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookRepo.save(book), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable String id) {
        Book toDelete = bookRepo.findById(id).orElse(null);
        if (toDelete == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        bookRepo.delete(toDelete);
        return new ResponseEntity<>(toDelete, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody Book book) {
        Book b = bookRepo.findById(id).orElse(null);
        if (b == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        b.setPrice(book.getPrice());
        b.setPublishingDate(book.getPublishingDate());
        b.setTitle(book.getTitle());
        bookRepo.save(b);
        return new ResponseEntity<>(b, HttpStatus.OK);
    }
}

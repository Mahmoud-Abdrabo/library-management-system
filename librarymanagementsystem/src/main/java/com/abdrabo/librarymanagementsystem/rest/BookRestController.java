package com.abdrabo.librarymanagementsystem.rest;

import com.abdrabo.librarymanagementsystem.entity.Book;
import com.abdrabo.librarymanagementsystem.exception.BadRequestException;
import com.abdrabo.librarymanagementsystem.exception.NotFoundException;
import com.abdrabo.librarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieve a list of all books.
     * */
    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.findAll();
    }

    /**
     * Retrieve details of a specific book by ID.
     * */
    @GetMapping("/{id}")
    public Book getBook(@PathVariable("id") Long id){
        return bookService.findById(id);
    }

    /**
     * Add a new book to the library.
     * */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody Book book){
        return bookService.save(book);
    }

    /**
     * Update an existing book's information.
     * */
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable("id") Long id, @RequestBody Book book){
        if (id != book.getId()){
            throw new BadRequestException("id on path doesn't match body");
        }
        else {
            return bookService.update(book);
        }
    }

    /**
     * Remove a book from the library.
     * */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteBook(@PathVariable("id") Long id){
        bookService.deleteById(id);
    }
}

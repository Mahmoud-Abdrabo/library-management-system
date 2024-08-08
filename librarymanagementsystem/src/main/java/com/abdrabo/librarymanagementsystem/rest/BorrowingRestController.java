package com.abdrabo.librarymanagementsystem.rest;

import com.abdrabo.librarymanagementsystem.entity.Book;
import com.abdrabo.librarymanagementsystem.entity.BorrowingRecord;
import com.abdrabo.librarymanagementsystem.entity.Patron;
import com.abdrabo.librarymanagementsystem.service.BookService;
import com.abdrabo.librarymanagementsystem.service.BorrowingRecordService;
import com.abdrabo.librarymanagementsystem.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BorrowingRestController {

    private final BorrowingRecordService borrowingRecordService;
    private final BookService bookService;
    private final PatronService patronService;

    @Autowired
    public BorrowingRestController(BorrowingRecordService borrowingRecordService, BookService bookService,
                                   PatronService patronService) {
        this.borrowingRecordService = borrowingRecordService;
        this.bookService = bookService;
        this.patronService = patronService;
    }

    /**
     * Retrieve a list of all the borrowing records.
     * */
    @GetMapping("/records")
    public List<BorrowingRecord> getAllBorrowingRecords(){
        return borrowingRecordService.findAll();
    }

    /**
     * Allow a patron to borrow a book.
     * */
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BorrowingRecord borrowBook(@PathVariable Long bookId, @PathVariable Long patronId,
                                      @RequestBody BorrowingRecord borrowingRecord){
        Book book = bookService.findById(bookId);

        Patron patron = patronService.findById(patronId);

        BorrowingRecord record = new BorrowingRecord(borrowingRecord.getBorrowDate(), null,
                book, patron);
        return borrowingRecordService.save(record);
    }

    /**
     * Record the return of a borrowed book by a patron.
     * */
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public BorrowingRecord returnBook(@PathVariable Long bookId, @PathVariable Long patronId,
                                      @RequestBody BorrowingRecord borrowingRecord) {

        BorrowingRecord record = borrowingRecordService
                .findByBookIdAndPatronId(bookId, patronId);

        record.setReturnDate(borrowingRecord.getReturnDate());
        return borrowingRecordService.update(record);
    }
}

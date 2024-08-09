package com.abdrabo.librarymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "borrowing_record")
@Data
@NoArgsConstructor
public class BorrowingRecord {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "borrow_date")
    @NotNull(message = "Borrow date is required")
    @PastOrPresent(message = "Borrow date must be present or in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate borrowDate;

    @Column(name = "return_date")
    @FutureOrPresent(message = "Return date must be present or in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;

    public BorrowingRecord(LocalDate borrowDate, LocalDate returnDate, Book book, Patron patron) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.book = book;
        this.patron = patron;
    }
}

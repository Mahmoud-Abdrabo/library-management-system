package com.abdrabo.librarymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be less than 255 characters")
    private String title;

    @Column(name = "author")
    @NotBlank(message = "Author is required")
    @Size(max = 255, message = "Author must be less than 255 characters")
    private String author;

    @Column(name = "publication_year")
    @NotBlank(message = "Publication year is required")
    @Size(min = 4, max = 4, message = "Publication year must be 4 digits")
    @Pattern(regexp = "\\d{4}", message = "Publication year must be a 4-digit number")
    private String publicationYear;

    @Column(name = "isbn")
    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 14, message = "ISBN must be between 10 and 13 characters")
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN must be either 10 or 13 digits long")
    private String isbn;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BorrowingRecord> borrowingRecords;

    public Book(String title, String author, String publicationYear, String isbn) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
    }
}

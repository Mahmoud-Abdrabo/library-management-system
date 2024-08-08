package com.abdrabo.librarymanagementsystem.dao;

import com.abdrabo.librarymanagementsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

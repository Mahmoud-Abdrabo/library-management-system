package com.abdrabo.librarymanagementsystem.service;

import com.abdrabo.librarymanagementsystem.dao.BookRepository;
import com.abdrabo.librarymanagementsystem.entity.Book;
import com.abdrabo.librarymanagementsystem.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + id));
    }

    @Override
    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book update(Book book) {
        if(!bookRepository.existsById(book.getId())){
            throw new NotFoundException("Book not found with id: " + book.getId());
        }
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}

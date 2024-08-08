package com.abdrabo.librarymanagementsystem.service;

import com.abdrabo.librarymanagementsystem.entity.Patron;

import java.util.List;

public interface PatronService {

    List<Patron> findAll();

    Patron findById(Long id);

    Patron save(Patron patron);

    Patron update(Patron patron);

    void deleteById(Long id);
}

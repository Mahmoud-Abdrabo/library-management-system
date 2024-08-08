package com.abdrabo.librarymanagementsystem.dao;

import com.abdrabo.librarymanagementsystem.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {
}

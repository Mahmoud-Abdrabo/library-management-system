package com.abdrabo.librarymanagementsystem.service;

import com.abdrabo.librarymanagementsystem.entity.BorrowingRecord;

import java.util.List;
import java.util.Optional;

public interface BorrowingRecordService {

    List<BorrowingRecord> findAll();

    BorrowingRecord findById(Long id);

    BorrowingRecord save(BorrowingRecord borrowingRecord);

    BorrowingRecord update(BorrowingRecord borrowingRecord);

    void deleteById(Long id);

    BorrowingRecord findByBookIdAndPatronId(Long bookId, Long patronId);
}

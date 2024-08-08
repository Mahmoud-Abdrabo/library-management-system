package com.abdrabo.librarymanagementsystem.service;

import com.abdrabo.librarymanagementsystem.dao.BorrowingRecordRepository;
import com.abdrabo.librarymanagementsystem.entity.BorrowingRecord;
import com.abdrabo.librarymanagementsystem.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService{

    private final BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    public BorrowingRecordServiceImpl(BorrowingRecordRepository borrowingRecordRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BorrowingRecord> findAll() {
        return borrowingRecordRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public BorrowingRecord findById(Long id) {
        return borrowingRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Record not found with id: " + id));
    }

    @Override
    @Transactional
    public BorrowingRecord save(BorrowingRecord borrowingRecord) {
        return borrowingRecordRepository.save(borrowingRecord);
    }

    @Override
    @Transactional
    public BorrowingRecord update(BorrowingRecord borrowingRecord) {
        if(!borrowingRecordRepository.existsById(borrowingRecord.getId())){
            throw new NotFoundException("Record not found with id: " + borrowingRecord.getId());
        }
        return borrowingRecordRepository.save(borrowingRecord);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if(!borrowingRecordRepository.existsById(id)){
            throw new NotFoundException("Record not found with id: " + id);
        }
        borrowingRecordRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public BorrowingRecord findByBookIdAndPatronId(Long bookId, Long patronId) {
        return borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId)
                .orElseThrow(() -> new NotFoundException(
                        "Record not found for bookId: " + bookId + " and patronId: " + patronId));
    }
}

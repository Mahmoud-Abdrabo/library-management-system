package com.abdrabo.librarymanagementsystem.service;

import com.abdrabo.librarymanagementsystem.dao.PatronRepository;
import com.abdrabo.librarymanagementsystem.entity.Patron;
import com.abdrabo.librarymanagementsystem.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatronServiceImpl implements PatronService{

    private final PatronRepository patronRepository;

    @Autowired
    public PatronServiceImpl(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Patron> findAll() {
        return patronRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Patron findById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patron not found with id: " + id));
    }

    @Override
    @Transactional
    public Patron save(Patron patron) {
        return patronRepository.save(patron);
    }

    @Override
    @Transactional
    public Patron update(Patron patron) {
        if(!patronRepository.existsById(patron.getId())){
            throw new NotFoundException("Patron not found with id: " + patron.getId());
        }
        return patronRepository.save(patron);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new NotFoundException("Patron not found with id: " + id);
        }
        patronRepository.deleteById(id);
    }
}

package com.abdrabo.librarymanagementsystem.rest;

import com.abdrabo.librarymanagementsystem.entity.Patron;
import com.abdrabo.librarymanagementsystem.exception.BadRequestException;
import com.abdrabo.librarymanagementsystem.exception.NotFoundException;
import com.abdrabo.librarymanagementsystem.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronRestController {
    
    private final PatronService patronService;
    
    @Autowired
    public PatronRestController(PatronService patronService) {
        this.patronService = patronService;
    }

    /**
     * Retrieve a list of all patrons.
     * */
    @GetMapping
    public List<Patron> getAllPatrons(){
        return patronService.findAll();
    }

    /**
     * Retrieve details of a specific patron by ID.
     * */
    @GetMapping("/{id}")
    public Patron getPatron(@PathVariable("id") Long id){
        return patronService.findById(id);
    }

    /**
     * Add a new patron to the library.
     * */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patron addPatron(@RequestBody Patron patron){
        return patronService.save(patron);
    }

    /**
     * Update an existing patron's information.
     * */
    @PutMapping("/{id}")
    public Patron updatePatron(@PathVariable("id") Long id, @RequestBody Patron patron){
        if (id != patron.getId()){
            throw new BadRequestException("id on path doesn't match body");
        }
        else {
            return patronService.update(patron);
        }
    }

    /**
     * Remove a patron from the system.
     * */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deletePatron(@PathVariable("id") Long id){
        patronService.deleteById(id);
    }
}

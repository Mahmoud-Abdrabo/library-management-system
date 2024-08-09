package com.abdrabo.librarymanagementsystem.rest;

import com.abdrabo.librarymanagementsystem.entity.Book;
import com.abdrabo.librarymanagementsystem.entity.BorrowingRecord;
import com.abdrabo.librarymanagementsystem.entity.Patron;
import com.abdrabo.librarymanagementsystem.service.BorrowingRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BorrowingRecordRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingRecordService borrowingRecordService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule
    }

    @Test
    @WithMockUser(username = "john", password = "test",roles = {"ADMIN"})
    public void testGetAllBorrowingRecords() throws Exception {
        Book book = new Book("Title", "Author", "2024", "1234567890");
        book.setId(1L);

        Patron patron = new Patron("John Doe", "john.doe@example.com");
        patron.setId(1L);

        BorrowingRecord record1 = new BorrowingRecord(LocalDate.now(), LocalDate.now().plusDays(7), book, patron);
        record1.setId(1L);

        BorrowingRecord record2 = new BorrowingRecord(LocalDate.now(), LocalDate.now().plusDays(7), book, patron);
        record2.setId(2L);

        List<BorrowingRecord> records = Arrays.asList(record1, record2);

        when(borrowingRecordService.findAll()).thenReturn(records);

        mockMvc.perform(get("/api/records")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].borrowDate").value(record1.getBorrowDate().toString()))
                .andExpect(jsonPath("$[1].borrowDate").value(record2.getBorrowDate().toString()))
                .andExpect(jsonPath("$[0].returnDate").value(record1.getReturnDate().toString()))
                .andExpect(jsonPath("$[1].returnDate").value(record2.getReturnDate().toString()))
                .andExpect(jsonPath("$[0].book.id").value(1L))
                .andExpect(jsonPath("$[1].book.id").value(1L))
                .andExpect(jsonPath("$[0].patron.id").value(1L))
                .andExpect(jsonPath("$[1].patron.id").value(1L));
    }

    @Test
    @WithMockUser(username = "john", password = "test",roles = {"ADMIN"})
    public void testCreateBorrowingRecord() throws Exception {
        Book book = new Book("Title", "Author", "2024", "1234567890");
        book.setId(1L);

        Patron patron = new Patron("John Doe", "john.doe@example.com");
        patron.setId(1L);

        BorrowingRecord record = new BorrowingRecord(LocalDate.now(), LocalDate.now().plusDays(7), book, patron);

        when(borrowingRecordService.save(any(BorrowingRecord.class))).thenReturn(record);

        mockMvc.perform(post("/api/borrow/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.borrowDate").value(record.getBorrowDate().toString()))
                .andExpect(jsonPath("$.returnDate").value(record.getReturnDate().toString()))
                .andExpect(jsonPath("$.book.id").value(1L))
                .andExpect(jsonPath("$.patron.id").value(1L));
    }

    @Test
    @WithMockUser(username = "john", password = "test",roles = {"ADMIN"})
    public void testUpdateBorrowingRecord() throws Exception {
        Book book = new Book("Title", "Author", "2024", "1234567890");
        book.setId(1L);

        Patron patron = new Patron("John Doe", "john.doe@example.com");
        patron.setId(1L);

        BorrowingRecord record = new BorrowingRecord(LocalDate.now(), LocalDate.now().plusDays(7), book, patron);
        record.setId(1L);

        when(borrowingRecordService.findById(anyLong())).thenReturn(record);

        when(borrowingRecordService.update(any(BorrowingRecord.class))).thenReturn(record);

        mockMvc.perform(put("/api/return/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.borrowDate").value(record.getBorrowDate().toString()))
                .andExpect(jsonPath("$.returnDate").value(record.getReturnDate().toString()))
                .andExpect(jsonPath("$.book.id").value(1L))
                .andExpect(jsonPath("$.patron.id").value(1L));
    }
}

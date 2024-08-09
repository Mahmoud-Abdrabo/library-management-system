package com.abdrabo.librarymanagementsystem.rest;


import com.abdrabo.librarymanagementsystem.entity.Book;
import com.abdrabo.librarymanagementsystem.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "john", password = "test",roles = {"ADMIN"})
    public void testGetAllBooks() throws Exception {
        Book book1 = new Book("Title1", "Author1", "2024", "1234567890");
        Book book2 = new Book("Title2", "Author2", "2023", "0987654321");
        List<Book> books = Arrays.asList(book1, book2);

        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Title1"))
                .andExpect(jsonPath("$[1].title").value("Title2"))
                .andExpect(jsonPath("$[0].author").value("Author1"))
                .andExpect(jsonPath("$[1].author").value("Author2"));
    }

    @Test
    @WithMockUser(username = "john", password = "test",roles = {"ADMIN"})
    public void testGetBookById() throws Exception {
        Book book = new Book("Title", "Author", "2024", "1234567890");
        book.setId(1L);

        when(bookService.findById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.author").value("Author"));
    }

    @Test
    @WithMockUser(username = "john", password = "test",roles = {"ADMIN"})
    public void testCreateBook() throws Exception {
        Book book = new Book("Title", "Author", "2024", "1234567890");

        when(bookService.save(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "john", password = "test",roles = {"ADMIN"})
    public void testUpdateBook() throws Exception {
        Book book = new Book("Updated Title", "Updated Author", "2024", "1234567890");
        book.setId(1L);

        when(bookService.update(any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.author").value("Updated Author"));
    }

    @Test
    @WithMockUser(username = "john", password = "test",roles = {"ADMIN"})
    public void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isResetContent());
    }
}

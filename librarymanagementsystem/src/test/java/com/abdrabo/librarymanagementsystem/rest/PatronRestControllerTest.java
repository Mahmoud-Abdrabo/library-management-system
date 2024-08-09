package com.abdrabo.librarymanagementsystem.rest;

import com.abdrabo.librarymanagementsystem.entity.Patron;
import com.abdrabo.librarymanagementsystem.service.PatronService;
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
public class PatronRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "john", password = "test",roles = {"ADMIN"})
    public void testGetAllPatrons() throws Exception {
        Patron patron1 = new Patron("John Doe", "john.doe@example.com");
        Patron patron2 = new Patron("Jane Smith", "jane.smith@example.com");
        List<Patron> patrons = Arrays.asList(patron1, patron2);

        when(patronService.findAll()).thenReturn(patrons);

        mockMvc.perform(get("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"))
                .andExpect(jsonPath("$[0].contactInformation").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].contactInformation").value("jane.smith@example.com"));
    }

    @Test
    @WithMockUser(username = "john", password = "test",roles = {"ADMIN"})
    public void testGetPatronById() throws Exception {
        Patron patron = new Patron("John Doe", "john.doe@example.com");
        patron.setId(1L);

        when(patronService.findById(1L)).thenReturn(patron);

        mockMvc.perform(get("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.contactInformation").value("john.doe@example.com"));
    }

    @Test
    @WithMockUser(username = "john", password = "test",roles = {"ADMIN"})
    public void testCreatePatron() throws Exception {
        Patron patron = new Patron("John Doe", "john.doe@example.com");

        when(patronService.save(any(Patron.class))).thenReturn(patron);

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patron)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.contactInformation").value("john.doe@example.com"));
    }

    @Test
    @WithMockUser(username = "john", password = "test",roles = {"ADMIN"})
    public void testUpdatePatron() throws Exception {
        Patron patron = new Patron("Updated Name", "updated.email@example.com");
        patron.setId(1L);

        when(patronService.update(any(Patron.class))).thenReturn(patron);

        mockMvc.perform(put("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patron)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.contactInformation").value("updated.email@example.com"));
    }

    @Test
    @WithMockUser(username = "john", password = "test",roles = {"ADMIN"})
    public void testDeletePatron() throws Exception {
        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isResetContent());
    }
}

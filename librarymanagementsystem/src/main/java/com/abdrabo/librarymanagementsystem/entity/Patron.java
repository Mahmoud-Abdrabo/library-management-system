package com.abdrabo.librarymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "patron")
@Data
@NoArgsConstructor
public class Patron {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @Column(name = "contact_information")
    @NotBlank(message = "Contact information is required")
    @Email(message = "Contact information must be a valid email address")
    private String contactInformation;

    @OneToMany(mappedBy = "patron", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BorrowingRecord> borrowingRecords;

    public Patron(String name, String contactInformation) {
        this.name = name;
        this.contactInformation = contactInformation;
    }
}

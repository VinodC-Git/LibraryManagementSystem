package com.library.System.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Book {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
          Long id;
        @Column(name = "isbn")
          @NotNull String isbn;
        @Column(name = "title")
          @NotNull String title;
        @Column(name = "author")
        @NotNull String author;
        @ManyToOne
          Borrower borrowerTakenBy;

}
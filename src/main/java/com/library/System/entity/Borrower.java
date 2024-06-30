package com.library.System.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Table(name = "borrower", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "email"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private @NotNull String name;
    @Column(name = "emailId")
    @Email
    @NotNull
    String emailAddress;
    /*  @OneToMany(mappedBy = "borrowerTakenBy")
      private Collection<Book> books;
  */
    @OneToMany(mappedBy = "borrowerTakenBy")
    private Set<Book> books = new HashSet<>();


}

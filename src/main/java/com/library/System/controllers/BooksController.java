package com.library.System.controllers;

import com.library.System.Repo.BookRepo;
import com.library.System.dto.BookResponseDTO;
import com.library.System.entity.Book;
import com.library.System.services.BookService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@RestController
@RequestMapping("/libraryApi/books")
public class BooksController {

    @Autowired
    private final BookService bookService;

    @Autowired
    private final ModelMapper modelMapper;

    public BooksController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/getAllBooks")
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/bookId/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/registerBook")
    public ResponseEntity<BookResponseDTO> registeringTheBook(@RequestBody @Valid BookResponseDTO bookResponseDTO) throws FileAlreadyExistsException {

        return ResponseEntity.ok(bookService.registerBook(bookResponseDTO));
    }


    @PatchMapping("/borrowId/{borrowerId}/returnBookId/{bookId}")
    public ResponseEntity<BookResponseDTO> returningTheBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        try {
            return ResponseEntity.ok(bookService.returnBook(borrowerId, bookId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/borrowId/{borrowerId}/bookId/{bookId}")
    public ResponseEntity<BookResponseDTO> borrowingTheBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        try {
            return ResponseEntity.ok(bookService.borrowBook(borrowerId, bookId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}

package com.library.System.controllers;

import com.library.System.dto.BookResponseDTO;
import com.library.System.entity.Book;
import com.library.System.services.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

@RestController
@RequestMapping("/libraryApi/books")
//@Api(value = "Book Management System"
@Slf4j
@Tag(name = "BookController", description = "Endpoints for managing books")
public class BooksController {

    @Autowired
    private final BookService bookService;

    @Autowired
    private final ModelMapper modelMapper;

    public BooksController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    //private static final Logger log = (Logger) LoggerFactory.getLogger(BooksController.class);
   // static Logger log = getLogger(BooksController.log.getName());



    @GetMapping("/getAllBooks")
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        log.info("Fetching all books");
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/bookId/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        log.info("Fetching book with ID: {}" + id);
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/registerBook")
    public ResponseEntity<?> registeringTheBook(@RequestBody @Valid BookResponseDTO bookResponseDTO, BindingResult result) throws FileAlreadyExistsException {
        if (result.hasErrors()) {
            log.error("Validation errors: {}" + result.getAllErrors());
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        log.info("Registering a new book: {}" + bookResponseDTO);
        return ResponseEntity.ok(bookService.registerBook(bookResponseDTO));
    }


    @PatchMapping("/borrowId/{borrowerId}/returnBookId/{bookId}")
    public ResponseEntity<BookResponseDTO> returningTheBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        log.info("Returning book with ID: {}" + bookId);
        try {
            return ResponseEntity.ok(bookService.returnBook(borrowerId, bookId));
        } catch (Exception e) {
            log.error("Error returning book with ID: {}" + bookId + " " + e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/borrowId/{borrowerId}/bookId/{bookId}")
    public ResponseEntity<BookResponseDTO> borrowingTheBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        log.info("Borrowing book with ID: {} by borrower with ID: {}" + bookId + " " + borrowerId);
        try {
            return ResponseEntity.ok(bookService.borrowBook(borrowerId, bookId));
        } catch (Exception e) {
            log.error("Error borrowing book with ID: {}" + bookId + " " + e);
            return ResponseEntity.badRequest().body(null);
        }
    }

}

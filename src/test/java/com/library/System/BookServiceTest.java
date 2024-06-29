package com.library.System;

import com.library.System.Repo.BookRepo;
import com.library.System.Repo.BorrowerRepo;
import com.library.System.dto.BookResponseDTO;
import com.library.System.entity.Book;
import com.library.System.entity.Borrower;
import com.library.System.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.nio.file.FileAlreadyExistsException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private BorrowerRepo borrowerRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private Borrower borrower;
    private BookResponseDTO bookResponseDTO;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setIsbn("123456");
        book.setTitle("Title");
        book.setAuthor("Author");

        borrower = new Borrower();
        borrower.setId(1L);

        bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setId(1L);
        bookResponseDTO.setIsbn("123456");
        bookResponseDTO.setTitle("Title");
        bookResponseDTO.setAuthor("Author");
    }

    @Test
    void registerBook_success() throws FileAlreadyExistsException {
        when(modelMapper.map(any(BookResponseDTO.class), eq(Book.class))).thenReturn(book);
        when(bookRepo.save(any(Book.class))).thenReturn(book);
        when(modelMapper.map(any(Book.class), eq(BookResponseDTO.class))).thenReturn(bookResponseDTO);

        BookResponseDTO result = bookService.registerBook(bookResponseDTO);

        assertNotNull(result);
        assertEquals(bookResponseDTO.getId(), result.getId());
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    void getAllBooks_success() {
        when(bookRepo.findAll()).thenReturn(Collections.singletonList(book));
        when(modelMapper.map(any(Book.class), eq(BookResponseDTO.class))).thenReturn(bookResponseDTO);

        List<BookResponseDTO> books = bookService.getAllBooks();

        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        assertEquals(bookResponseDTO.getId(), books.get(0).getId());
        verify(bookRepo, times(1)).findAll();
    }

    @Test
    void getBookById_success() {
        when(bookRepo.findById(anyLong())).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals(book.getId(), result.get().getId());
        verify(bookRepo, times(1)).findById(1L);
    }


}

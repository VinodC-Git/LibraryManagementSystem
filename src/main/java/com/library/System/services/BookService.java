package com.library.System.services;

import com.library.System.Repo.BookRepo;
import com.library.System.Repo.BorrowerRepo;
import com.library.System.dto.BookResponseDTO;
import com.library.System.entity.Book;
import com.library.System.entity.Borrower;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Logger logger = Logger.getLogger(BookService.class.getName());

    @Autowired
    private final BookRepo bookRepo;

    @Autowired
    private final BorrowerRepo borrowerRepo;

    @Autowired
    private final ModelMapper modelMapper;

    public BookService(BookRepo bookRepo, BorrowerRepo borrowerRepo, ModelMapper modelMapper) {
        this.bookRepo = bookRepo;
        this.borrowerRepo = borrowerRepo;
        this.modelMapper = modelMapper;
    }

    public List<BookResponseDTO> getAllBooks() {
        List<Book> books = bookRepo.findAll();
        return books.stream()
                .map(book -> modelMapper.map(book, BookResponseDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepo.findById(id);
    }

    public BookResponseDTO borrowBook(Long borrowerId, Long bookId) throws Exception {
        Optional<Book> checkBookId = bookRepo.findById(bookId);
        Optional<Borrower> checkBorrowerId = borrowerRepo.findById(borrowerId);

        Book checkBook = checkBookId.orElseThrow(ChangeSetPersister.NotFoundException::new);
        Borrower checkBorrower = checkBorrowerId.orElseThrow(ChangeSetPersister.NotFoundException::new);

        if (checkBorrower.getBooks().contains(checkBook)) {
            throw new FileAlreadyExistsException("This book is already in possession of the borrower with id: " + borrowerId);
        }

        checkBook.setBorrowerTakenBy(checkBorrower);
        checkBorrower.getBooks().add(checkBook);
        borrowerRepo.save(checkBorrower);
        checkBook = bookRepo.save(checkBook);
        return modelMapper.map(checkBook, BookResponseDTO.class);
    }

    public BookResponseDTO returnBook(Long borrowerId, Long bookId) throws Exception {
        Optional<Book> checkBookId = bookRepo.findById(bookId);
        Optional<Borrower> checkBorrowerId = borrowerRepo.findById(borrowerId);

        Book checkBook = checkBookId.orElseThrow(ChangeSetPersister.NotFoundException::new);
        Borrower checkBorrower = checkBorrowerId.orElseThrow(ChangeSetPersister.NotFoundException::new);

        if (!checkBorrower.getBooks().contains(checkBook)) {
            logger.warning(" The identified borrower has no record of borrowing this book, borrowerId:{}, bookId:{}" + borrowerId + " " + bookId);
            throw new FileAlreadyExistsException("The given borrower has not borrowed this book.: " + borrowerId);
        }

        checkBook.setBorrowerTakenBy(null);
        checkBorrower.getBooks().remove(checkBook);
        borrowerRepo.save(checkBorrower);
        checkBook = bookRepo.save(checkBook);
        return modelMapper.map(checkBook, BookResponseDTO.class);
    }

    public BookResponseDTO registerBook(BookResponseDTO bookResponseDTO) throws FileAlreadyExistsException {
        validateBookDto(bookResponseDTO);
        System.out.println("log555" + bookResponseDTO);

        Book book = modelMapper.map(bookResponseDTO, Book.class);
        Book savedBook = bookRepo.save(book);
        return modelMapper.map(savedBook, BookResponseDTO.class);
    }

    private void validateBookDto(BookResponseDTO bookResponseDTO) throws FileAlreadyExistsException {
        List<Book> existingBooks = bookRepo.findAllByIsbn(bookResponseDTO.getIsbn());

        for (Book book : existingBooks) {
            if (!book.getAuthor().equals(bookResponseDTO.getAuthor()) || !book.getTitle().equals(bookResponseDTO.getTitle())) {
                throw new FileAlreadyExistsException("Book already exist with the same title and author");
            }
        }
    }

}




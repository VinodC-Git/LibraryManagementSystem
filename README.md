# Library Management System

LibraryManagementSystem is a RESTful API's for managing system. The API allows users to register borrowers, register books, list all books, borrow books, and return borrowed books.

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- mySQL Database
- Lombok
- Maven
- Docker

## Features

- Register a new borrower to the library.
- Register a new book to the library.
- Get a list of all books in the library.
- Borrow a book.
- Return a borrowed book.


## Build the project:

1. mvn clean install


##Docker
- docker build -t librarymanagementsystem .
- docker run -p 8088:8088 librarymanagementsystem

##API Endpoints
    - /libraryApi/books
POST /registerBorrower: Register a new borrower.
GET /getAllBorrowers: Get a list of all borrowers.
GET /{id}: Get borrower by ID.
POST /registerBook: Register a new book.
GET  "/getAllBooks": Get a list of all books.
GET /bookId/{id}: Get book by ID.
Patch /borrowId/{borrowerId}/returnBookId/{bookId}: returningTheBook
Patch /borrowId/{borrowerId}/bookId/{bookId} : borrowingTheBook

##ExceptionHandling
 Used some pre-defined Exception but adde some user-defined exceptiosn in the exception package
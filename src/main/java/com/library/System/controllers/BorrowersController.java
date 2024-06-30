package com.library.System.controllers;

import com.library.System.dto.BorrowerResponseDTO;
import com.library.System.services.BorrowerService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

@RestController
@RequestMapping("/libraryApi/borrowers")
//@Api(value = "Borrower Management System")
@Slf4j
public class BorrowersController {

   // private static final Logger log = (Logger) LoggerFactory.getLogger(BorrowersController.class);

   // static Logger log = getLogger(BooksController.log.getName());


    @Autowired
    private final BorrowerService borrowerService;

    public BorrowersController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @GetMapping("/getAllBorrowers")
    // @ApiOperation(value = "Get a list of all borrowers")
    public ResponseEntity<List<BorrowerResponseDTO>> getAllBorrowers() {
        return ResponseEntity.ok(borrowerService.getAllBorrowers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowerResponseDTO> getBorrowerById(@PathVariable Long id) {
        log.info("Fetching borrower with ID: {}" + id);
        return borrowerService.getBorrowerById(id)
                .map(borrower -> {
                    log.info("Successfully fetched borrower: {}" + borrower);
                    return ResponseEntity.ok(borrower);
                })
                .orElseGet(() -> {
                    log.error("Borrower with ID: {} not found" + id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping("/registerBorrower")
    public ResponseEntity<?> registerBorrower(@RequestBody @Validated BorrowerResponseDTO borrowerResponseDTO, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Validation errors: {}" + result.getAllErrors());
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        log.info("Registering a new borrower: {}" + borrowerResponseDTO);
        try {
            BorrowerResponseDTO registeredBorrower = borrowerService.registerBorrower(borrowerResponseDTO);
            log.info("Successfully registered borrower: {}" + registeredBorrower);
            return ResponseEntity.ok(registeredBorrower);
        } catch (Exception e) {
            log.error("Error registering borrower: {}" + borrowerResponseDTO + " " + e);
            return ResponseEntity.badRequest().build();
        }
    }


}

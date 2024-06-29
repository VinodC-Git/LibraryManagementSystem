package com.library.System.controllers;

import com.library.System.dto.BorrowerResponseDTO;
import com.library.System.services.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/libraryApi/borrowers")
public class BorrowersController {

    @Autowired
    private final BorrowerService borrowerService;

    public BorrowersController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @GetMapping("/getAllBorrowers")
    public ResponseEntity<List<BorrowerResponseDTO>> getAllBorrowers() {
        return ResponseEntity.ok(borrowerService.getAllBorrowers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowerResponseDTO> getBorrowerById(@PathVariable Long id) {
        return borrowerService.getBorrowerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/registerBorrower")
    public ResponseEntity<BorrowerResponseDTO> registerBorrower(@RequestBody @Validated BorrowerResponseDTO borrowerResponseDTO) {
        System.out.println("borrowerResponseDTO:"+borrowerResponseDTO);
        return ResponseEntity.ok(borrowerService.registerBorrower(borrowerResponseDTO));
    }


}

package com.library.System.services;

import com.library.System.Repo.BorrowerRepo;
import com.library.System.dto.BorrowerResponseDTO;
import com.library.System.entity.Borrower;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BorrowerService {
    @Autowired
    private final BorrowerRepo borrowerRepo;

    @Autowired
    private final ModelMapper modelMapper;

    public BorrowerService(BorrowerRepo borrowerRepo, ModelMapper modelMapper) {
        this.borrowerRepo = borrowerRepo;
        this.modelMapper = modelMapper;
    }

    public BorrowerResponseDTO registerBorrower(BorrowerResponseDTO borrowerResponseDTO) {
        Borrower borrower = modelMapper.map(borrowerResponseDTO, Borrower.class);
        System.out.println("borrowerObj"+borrower);
        Borrower savedBorrower = borrowerRepo.save(borrower);
        return modelMapper.map(savedBorrower, BorrowerResponseDTO.class);
    }

    public List<BorrowerResponseDTO> getAllBorrowers() {
        return borrowerRepo.findAll().stream()
                .map(borrower -> modelMapper.map(borrower, BorrowerResponseDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<BorrowerResponseDTO> getBorrowerById(Long id) {
        return borrowerRepo.findById(id)
                .map(borrower -> modelMapper.map(borrower, BorrowerResponseDTO.class));
    }
}
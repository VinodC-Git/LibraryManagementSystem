package com.library.System;

import com.library.System.Repo.BorrowerRepo;
import com.library.System.dto.BorrowerResponseDTO;
import com.library.System.entity.Borrower;
import com.library.System.services.BorrowerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowerServiceTest {

    @Mock
    private BorrowerRepo borrowerRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BorrowerService borrowerService;

    private Borrower borrower;
    private BorrowerResponseDTO borrowerResponseDTO;

    @BeforeEach
    void setUp() {
        borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("John Doe");

        borrowerResponseDTO = new BorrowerResponseDTO();
        borrowerResponseDTO.setId(1L);
        borrowerResponseDTO.setName("John Doe");
    }

    @Test
    void registerBorrower_success() {
        when(modelMapper.map(any(BorrowerResponseDTO.class), eq(Borrower.class))).thenReturn(borrower);
        when(borrowerRepo.save(any(Borrower.class))).thenReturn(borrower);
        when(modelMapper.map(any(Borrower.class), eq(BorrowerResponseDTO.class))).thenReturn(borrowerResponseDTO);

        BorrowerResponseDTO result = borrowerService.registerBorrower(borrowerResponseDTO);

        assertNotNull(result);
        assertEquals(borrowerResponseDTO.getId(), result.getId());
        verify(borrowerRepo, times(1)).save(borrower);
    }

    @Test
    void getAllBorrowers_success() {
        when(borrowerRepo.findAll()).thenReturn(Collections.singletonList(borrower));
        when(modelMapper.map(any(Borrower.class), eq(BorrowerResponseDTO.class))).thenReturn(borrowerResponseDTO);

        List<BorrowerResponseDTO> borrowers = borrowerService.getAllBorrowers();

        assertFalse(borrowers.isEmpty());
        assertEquals(1, borrowers.size());
        assertEquals(borrowerResponseDTO.getId(), borrowers.get(0).getId());
        verify(borrowerRepo, times(1)).findAll();
    }

    @Test
    void getBorrowerById_success() {
        when(borrowerRepo.findById(anyLong())).thenReturn(Optional.of(borrower));
        when(modelMapper.map(any(Borrower.class), eq(BorrowerResponseDTO.class))).thenReturn(borrowerResponseDTO);

        Optional<BorrowerResponseDTO> result = borrowerService.getBorrowerById(1L);

        assertTrue(result.isPresent());
        assertEquals(borrowerResponseDTO.getId(), result.get().getId());
        verify(borrowerRepo, times(1)).findById(1L);
    }
}

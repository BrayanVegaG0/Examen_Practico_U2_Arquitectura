package com.banquito.core.sucursales.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.sucursales.dto.BranchCreateDTO;
import com.banquito.core.sucursales.dto.BranchHolidayDTO;
import com.banquito.core.sucursales.dto.BranchResponseDTO;
import com.banquito.core.sucursales.dto.BranchUpdateDTO;
import com.banquito.core.sucursales.mapper.BranchMapper;
import com.banquito.core.sucursales.model.Branch;
import com.banquito.core.sucursales.model.BranchHoliday;
import com.banquito.core.sucursales.repository.BranchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    public List<BranchResponseDTO> getAllBranches() {
        log.info("Retrieving all branches");
        return branchRepository.findAll().stream()
                .map(branchMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BranchResponseDTO createBranch(BranchCreateDTO dto) {
        log.info("Creating new branch with email: {}", dto.getEmailAddress());
        
        if (branchRepository.existsByEmailAddress(dto.getEmailAddress())) {
            log.error("Branch creation failed. Email {} already exists", dto.getEmailAddress());
            throw new IllegalArgumentException("Branch with this email already exists");
        }

        Branch branch = branchMapper.toEntity(dto);
        branch.setBranchHolidays(new ArrayList<>()); 
        
        Branch savedBranch = branchRepository.save(branch);
        log.info("Branch created successfully with ID: {}", savedBranch.getId());
        
        return branchMapper.toResponseDTO(savedBranch);
    }

    public BranchResponseDTO getBranchById(String id) {
        log.info("Retrieving branch with ID: {}", id);
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Branch not found with ID: {}", id);
                    return new NoSuchElementException("Branch not found with ID: " + id);
                });
        return branchMapper.toResponseDTO(branch);
    }

    @Transactional
    public BranchResponseDTO updateBranch(String id, BranchUpdateDTO dto) {
        log.info("Updating branch with ID: {}", id);
        
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Branch not found with ID: " + id));

        branchMapper.updateEntityFromDTO(dto, branch);
        
        Branch updatedBranch = branchRepository.save(branch);
        log.info("Branch updated successfully");
        
        return branchMapper.toResponseDTO(updatedBranch);
    }

    @Transactional
    public void addHoliday(String branchId, BranchHolidayDTO holidayDTO) {
        log.info("Adding holiday to branch ID: {}", branchId);
        
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new NoSuchElementException("Branch not found"));

        boolean exists = branch.getBranchHolidays().stream()
                .anyMatch(h -> h.getDate().isEqual(holidayDTO.getDate()));

        if (exists) {
            log.warn("Holiday already exists for date: {}", holidayDTO.getDate());
            throw new IllegalArgumentException("Holiday already exists for this date");
        }

        BranchHoliday holiday = branchMapper.toHolidayEntity(holidayDTO);
        branch.getBranchHolidays().add(holiday);
        branch.setLastModifiedDate(LocalDateTime.now());

        branchRepository.save(branch);
        log.info("Holiday added successfully");
    }

    @Transactional
    public void removeHoliday(String branchId, LocalDate date) {
        log.info("Removing holiday for date: {} from branch ID: {}", date, branchId);
        
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new NoSuchElementException("Branch not found"));

        boolean removed = branch.getBranchHolidays()
                .removeIf(h -> h.getDate().isEqual(date));

        if (!removed) {
            log.warn("No holiday found for date: {}", date);
            throw new NoSuchElementException("Holiday not found for the given date");
        }

        branch.setLastModifiedDate(LocalDateTime.now());
        branchRepository.save(branch);
        log.info("Holiday removed successfully");
    }

    public List<BranchHolidayDTO> getHolidays(String branchId) {
        log.info("Retrieving holidays for branch ID: {}", branchId);
        
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new NoSuchElementException("Branch not found"));

        return branch.getBranchHolidays().stream()
                .map(branchMapper::toHolidayDTO)
                .collect(Collectors.toList());
    }

    public boolean isHoliday(String branchId, LocalDate date) {
        log.info("Checking if date: {} is a holiday for branch ID: {}", date, branchId);
        
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new NoSuchElementException("Branch not found"));

        boolean isHoliday = branch.getBranchHolidays().stream()
                .anyMatch(h -> h.getDate().isEqual(date));
        
        log.info("Result for date {}: {}", date, isHoliday);
        return isHoliday;
    }
}
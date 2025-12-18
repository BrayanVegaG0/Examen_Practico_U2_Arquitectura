package com.banquito.core.sucursales.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.banquito.core.sucursales.dto.BranchCreateDTO;
import com.banquito.core.sucursales.dto.BranchHolidayDTO;
import com.banquito.core.sucursales.dto.BranchResponseDTO;
import com.banquito.core.sucursales.dto.BranchUpdateDTO;
import com.banquito.core.sucursales.model.Branch;
import com.banquito.core.sucursales.model.BranchHoliday;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BranchMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", constant = "ACTIVE")
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "lastModifiedDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "branchHolidays", ignore = true) 
    @Mapping(target = "version", ignore = true)
    Branch toEntity(BranchCreateDTO dto);

    BranchResponseDTO toResponseDTO(Branch entity);

    @Mapping(target = "lastModifiedDate", expression = "java(java.time.LocalDateTime.now())") // Actualizamos fecha mod
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "emailAddress", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "branchHolidays", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntityFromDTO(BranchUpdateDTO dto, @MappingTarget Branch entity);

    BranchHolidayDTO toHolidayDTO(BranchHoliday holiday);
    BranchHoliday toHolidayEntity(BranchHolidayDTO dto);
}
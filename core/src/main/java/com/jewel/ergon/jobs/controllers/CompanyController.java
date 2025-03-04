package com.jewel.ergon.jobs.controllers;


import com.jewel.ergon.jobs.model.Company;
import com.jewel.ergon.jobs.services.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value = "/api/v1/companies", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Company Controller", description = "API for managing companys")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    /**
     * Fetches  companies using eql .
     */
    @SneakyThrows
    @Operation(summary = "Get all companies", description = "Retrieve a list of all companies for the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of companies",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Company.class))})
    })
    @GetMapping("/getAllCompaniesByEql")
    public ResponseEntity<StandardResponse<Page<Company>>> getAllCompanies(@RequestParam(defaultValue = "") String query,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           @RequestParam(defaultValue = "id,asc") String[] sort) {


        // Parsing sort parameter
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);
        // Creating pageable instance
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Company> companies = companyService.filter(query,Company.class , pageable);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Companies retrieved successfully", companies));
    }


    /**
     * Fetches all companys for the user.
     */
    @SneakyThrows
    @Operation(summary = "Get all companies", description = "Retrieve a list of all companies for the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of companies",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Company.class))})
    })
    @GetMapping("/getAllCompanies")
    public ResponseEntity<StandardResponse<Page<Company>>> getAllCompanies(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           @RequestParam(defaultValue = "id,asc") String[] sort) {


        // Parsing sort parameter
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);
        // Creating pageable instance
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Company> companies = companyService.findAll(pageable);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Companies retrieved successfully", companies));
    }

    /**
     * Fetches a company by its unique identifier.
     */
    @Operation(summary = "Get company by ID", description = "Retrieve a specific company by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Company.class))}),
                    @ApiResponse(responseCode = "404", description = "Company not found", content = @Content)
    })
    @GetMapping("/getCompanyById/{id}")
    public ResponseEntity<StandardResponse<Company>> getCompanyById(@PathVariable Long id) {
        Optional<Company> company = companyService.findById(id);
        if (company.isPresent())
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Company retrieved successfully", company.orElseThrow()));
        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a new Company.
     */
    @Operation(summary = "Create a new company", description = "Add a new company to the list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Company.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/createCompany")
    public ResponseEntity<StandardResponse<Company>> createCompany(@Valid @RequestBody Company companyRequest) {
        Company createdCompany = companyService.save(companyRequest);
        return new ResponseEntity<>(new StandardResponse<>(HttpStatus.CREATED.value(), "Company created successfully", createdCompany), HttpStatus.CREATED);
    }

    /**
     * Updates an existing Company.
     */
    @SneakyThrows
    @Operation(summary = "Update an existing company", description = "Update details of an existing company by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Company.class))}),
            @ApiResponse(responseCode = "404", description = "Company not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/updateCompany/{id}")
    public ResponseEntity<StandardResponse<Company>> updateCompany(@PathVariable Long id, @Valid @RequestBody Company companyRequest) {
        Company updatedCompany = companyService.save(companyRequest);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Company updated successfully", updatedCompany));
    }

    /**
     * Deletes a company.
     */
    @Operation(summary = "Delete a company", description = "Remove a company from the list by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Company deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Company not found", content = @Content)
    })
    @DeleteMapping("/deleteCompany/{id}")
    public ResponseEntity<StandardResponse<Company>> deleteCompany(@PathVariable Long id) {
        Optional<Company> company = companyService.findById(id);
        if (company.isPresent()) {
            companyService.deleteById(id);
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.NO_CONTENT.value(), "Company with id: %d is deleted".formatted(id), null));
        }
        return ResponseEntity.noContent().build();
    }
}


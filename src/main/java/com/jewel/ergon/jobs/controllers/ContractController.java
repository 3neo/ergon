package com.jewel.ergon.jobs.controllers;

import com.jewel.ergon.jobs.model.Contract;
import com.jewel.ergon.jobs.services.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//TODO we fixed contract class to pass tests , so we should fix the other controllers exactly the same way
@RestController
@RequestMapping(value = "/api/v1/contracts", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Contract Controller", description = "API for managing contracts")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    /**
     * Fetches all contracts for the user.
     */
    @SneakyThrows
    @Operation(summary = "Get all contracts", description = "Retrieve a list of all contracts for the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of contracts",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class))})
    })
    @GetMapping("/getAllContracts")
    public ResponseEntity<StandardResponse<List<Contract>>> getAllContracts() {
        List<Contract> contracts = contractService.findAll();
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Contracts retrieved successfully", contracts));
    }

    /**
     * Fetches a contract by its unique identifier.
     */
    @Operation(summary = "Get contract by ID", description = "Retrieve a specific contract by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contract found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class))}),
            @ApiResponse(responseCode = "404", description = "Contract not found", content = @Content)
    })
    @GetMapping("/getContractById/{id}")
    public ResponseEntity<StandardResponse<Contract>> getContractById(@PathVariable Long id) {
        Optional<Contract> contract = contractService.findById(id);
        if (contract.isPresent())
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Contract retrieved successfully", contract.orElseThrow()));
        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a new Contract.
     */
    @Operation(summary = "Create a new contract", description = "Add a new contract to the list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contract created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/createContract")
    public ResponseEntity<StandardResponse<Contract>> createContract(@Valid @RequestBody Contract contractRequest) {
        Contract createdContract = contractService.save(contractRequest);
        return new ResponseEntity<>(new StandardResponse<>(HttpStatus.CREATED.value(), "Contract created successfully", createdContract), HttpStatus.CREATED);
    }

    /**
     * Updates an existing Contract.
     */
    @SneakyThrows
    @Operation(summary = "Update an existing contract", description = "Update details of an existing contract by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contract updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class))}),
            @ApiResponse(responseCode = "404", description = "Contract not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/updateContract/{id}")
    public ResponseEntity<StandardResponse<Contract>> updateContract(@PathVariable Long id, @Valid @RequestBody Contract contractRequest) {
        Contract updatedContract = contractService.save(contractRequest);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Contract updated successfully", updatedContract));
    }

    /**
     * Deletes a contract.
     */
    @Operation(summary = "Delete a contract", description = "Remove a contract from the list by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contract deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Contract not found", content = @Content)
    })
    @DeleteMapping("/deleteContract/{id}")
    public ResponseEntity<StandardResponse<Contract>> deleteContract(@PathVariable Long id) {
        Optional<Contract> contract = contractService.findById(id);
        if (contract.isPresent()) {
            contractService.deleteById(id);
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.NO_CONTENT.value(), "Contract with id: %d is deleted".formatted(id), null));
        }
        return ResponseEntity.noContent().build();
    }
}


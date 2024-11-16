package com.jewel.ergon.jobs.controllers;


import com.jewel.ergon.jobs.dto.DemandResponseDto;
import com.jewel.ergon.jobs.model.Demand;
import com.jewel.ergon.jobs.model.Status;
import com.jewel.ergon.jobs.services.DemandService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/demands")
@Tag(name = "Demand Controller", description = "API for managing job demands")
public class DemandController {

    private final DemandService demandService;

    @Autowired
    public DemandController(DemandService demandService) {
        this.demandService = demandService;
    }

    /**
     * Fetches all job demands for the user.
     */
    @SneakyThrows
    @Operation(summary = "Get all demands", description = "Retrieve a list of all job demands for the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of demands",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DemandResponseDto.class))})
    })
    @GetMapping("/getAllDemands")
    public ResponseEntity<StandardResponse<List<Demand>>> getAllDemands() {
        List<Demand> demands = demandService.findAll();
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Demands retrieved successfully", demands));
    }

    /**
     * Fetches a demand by its unique identifier.
     */
    @Operation(summary = "Get demand by ID", description = "Retrieve a specific job demand by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Demand found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DemandResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Demand not found", content = @Content)
    })
    @GetMapping("/getDemandById/{id}")
    public ResponseEntity<StandardResponse<Demand>> getDemandById(@PathVariable Long id) {
        Optional<Demand> demand = demandService.findById(id);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Demand retrieved successfully", demand.orElseThrow()));
    }

    /**
     * Creates a new job demand.
     */
    @Operation(summary = "Create a new demand", description = "Add a new job demand to the list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Demand created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DemandResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/createDemand")
    public ResponseEntity<StandardResponse<Demand>> createDemand(@Valid @RequestBody Demand demandRequest) {
        Demand createdDemand = demandService.save(demandRequest);
        return new ResponseEntity<>(new StandardResponse<>(HttpStatus.CREATED.value(), "Demand created successfully", createdDemand), HttpStatus.CREATED);
    }

    /**
     * Updates an existing job demand.
     */
    @SneakyThrows
    @Operation(summary = "Update an existing demand", description = "Update details of an existing job demand by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Demand updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DemandResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Demand not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/updateDemand/{id}")
    public ResponseEntity<StandardResponse<Demand>> updateDemand(@PathVariable Long id, @Valid @RequestBody Demand demandRequest) {
        Demand updatedDemand = demandService.updateDemand(id, demandRequest);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Demand updated successfully", updatedDemand));
    }

    /**
     * Deletes a job demand.
     */
    @Operation(summary = "Delete a demand", description = "Remove a job demand from the list by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Demand deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Demand not found", content = @Content)
    })
    @DeleteMapping("/deleteDemand/{id}")
    public ResponseEntity<StandardResponse<Demand>> deleteDemand(@PathVariable Long id) {
         demandService.deleteById(id);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.NO_CONTENT.value(), " Demand with id :id is deleted", null));
    }

    /**
     * Fetches demands by their status.
     */
    @Operation(summary = "Get demands by status", description = "Retrieve all job demands with a specific status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of demands by status",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DemandResponseDto.class))})
    })
    @GetMapping("/getDemandsByStatus")
    public ResponseEntity<StandardResponse<List<Demand>>> getDemandsByStatus(
            @RequestParam Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Demand> demandsByStatus = demandService.getDemandsByStatus(status, page, size).getContent();
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Demands with specified status retrieved successfully", demandsByStatus));
    }
}

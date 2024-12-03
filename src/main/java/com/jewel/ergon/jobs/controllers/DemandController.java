package com.jewel.ergon.jobs.controllers;

import com.jewel.ergon.jobs.model.Demand;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/v1/demands", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Demand Controller", description = "API for managing demands")
public class DemandController {

    private final DemandService demandService;

    @Autowired
    public DemandController(DemandService demandService) {
        this.demandService = demandService;
    }

    /**
     * Fetches all demands for the user.
     */
    @SneakyThrows
    @Operation(summary = "Get all demands", description = "Retrieve a list of all demands for the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of demands",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Demand.class))})
    })
    @GetMapping("/getAllDemands")
    public ResponseEntity<StandardResponse<Page<Demand>>> getAllDemands(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size,
                                                                        @RequestParam(defaultValue = "id,asc") String[] sort) {
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        Page<Demand> demands = demandService.findAll(pageable);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Demands retrieved successfully", demands));
    }

    /**
     * Fetches a demand by its unique identifier.
     */
    @Operation(summary = "Get demand by ID", description = "Retrieve a specific demand by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Demand found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Demand.class))}),
            @ApiResponse(responseCode = "404", description = "Demand not found", content = @Content)
    })
    @GetMapping("/getDemandById/{id}")
    public ResponseEntity<StandardResponse<Demand>> getDemandById(@PathVariable Long id) {
        Optional<Demand> demand = demandService.findById(id);
        if (demand.isPresent())
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Demand retrieved successfully", demand.orElseThrow()));
        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a new Demand.
     */
    @Operation(summary = "Create a new demand", description = "Add a new demand to the list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Demand created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Demand.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/createDemand")
    public ResponseEntity<StandardResponse<Demand>> createDemand(@Valid @RequestBody Demand demandRequest) {
        Demand createdDemand = demandService.save(demandRequest);
        return new ResponseEntity<>(new StandardResponse<>(HttpStatus.CREATED.value(), "Demand created successfully", createdDemand), HttpStatus.CREATED);
    }

    /**
     * Updates an existing Demand.
     */
    @SneakyThrows
    @Operation(summary = "Update an existing demand", description = "Update details of an existing demand by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Demand updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Demand.class))}),
            @ApiResponse(responseCode = "404", description = "Demand not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/updateDemand/{id}")
    public ResponseEntity<StandardResponse<Demand>> updateDemand(@PathVariable Long id, @Valid @RequestBody Demand demandRequest) {
        Demand updatedDemand = demandService.save(demandRequest);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Demand updated successfully", updatedDemand));
    }

    /**
     * Deletes a demand.
     */
    @Operation(summary = "Delete a demand", description = "Remove a demand from the list by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Demand deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Demand not found", content = @Content)
    })
    @DeleteMapping("/deleteDemand/{id}")
    public ResponseEntity<StandardResponse<Demand>> deleteDemand(@PathVariable Long id) {
        Optional<Demand> demand = demandService.findById(id);
        if (demand.isPresent()) {
            demandService.deleteById(id);
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.NO_CONTENT.value(), "Demand with id: %d is deleted".formatted(id), null));
        }
        return ResponseEntity.noContent().build();
    }
}


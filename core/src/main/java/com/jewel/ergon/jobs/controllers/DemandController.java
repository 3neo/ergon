package com.jewel.ergon.jobs.controllers;

import com.jewel.ergon.jobs.dto.ChartProjection;
import com.jewel.ergon.jobs.model.Demand;
import com.jewel.ergon.jobs.services.DemandService;
import com.jewel.ergon.jobs.services.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/v1/demands")
@Tag(name = "Demand Controller", description = "API for managing demands")
public class DemandController {

    private final DemandService demandService;

    @Autowired
    public DemandController(DemandService demandService) {
        this.demandService = demandService;
    }


    /**
     * Fetches  Demands using eql .
     */
    @SneakyThrows
    @Operation(summary = "Get all demands using EQL", description = "Retrieve a list of all demands using eql")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of demands",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Demand.class))})
    })
    @GetMapping("/getAllDemandsByEql")
    public ResponseEntity<StandardResponse<Page<Demand>>> getAllDemandsByEql(@RequestParam(defaultValue = "") String query,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size,
                                                                             @RequestParam(defaultValue = "id,asc") String[] sort) {


        // Parsing sort parameter
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);
        // Creating pageable instance
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Demand> demands = demandService.filter(query, Demand.class, pageable);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Demands retrieved successfully", demands));
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
                                                                        @RequestParam(defaultValue = "dateApplied,desc") String[] sort) {
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

    /**
     * Fetches a demand by its unique identifier.
     */
    @Operation(summary = "Get demand status count", description = "Retrieve count of demand by status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Demand status found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ChartProjection.class))}),
            @ApiResponse(responseCode = "404", description = "Demand status not found", content = @Content)
    })
    @GetMapping("/getDemandStatusCounts")
    public ResponseEntity<StandardResponse<List<ChartProjection>>> getDemandStatusCounts() {
        Optional<List<ChartProjection>> demandsPercentage = demandService.findDemandsPercentage();
        if (demandsPercentage.isPresent())
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Demand retrieved successfully", demandsPercentage.orElseThrow()));
        return ResponseEntity.notFound().build();
    }


    /**
     * Fetches a demands for las 7  days.
     */
    @Operation(summary = "Get demands for last week", description = "Retrieve count of demand for las 7 days.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Demand for las 7 days  found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ChartProjection.class))}),
            @ApiResponse(responseCode = "404", description = "Demands  not found", content = @Content)
    })
    @GetMapping("/getDemandsForLastWeek")
    public ResponseEntity<StandardResponse<List<ChartProjection>>> getDemandsForLastWeek() {
        Optional<List<ChartProjection>> demands7 = demandService.findDemandsForLas7days();
        if (demands7.isPresent())
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Demands for last 7 days  retrieved successfully", demands7.orElseThrow()));
        return ResponseEntity.notFound().build();
    }

    /**
     * Fetches a demands for las 7  days.
     */
    @Operation(summary = "Get total demands ", description = "Retrieve count of total demands demand .")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "total Demands found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ChartProjection.class))}),
            @ApiResponse(responseCode = "404", description = "total Demands  not found", content = @Content)
    })
    @GetMapping("/getTotalDemandsCount")
    public ResponseEntity<StandardResponse<Long>> getTotalDemandsCount() {
        Optional<Long> demandsCount = demandService.findTotalDemands();
        if (demandsCount.isPresent())
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "total Demands count retrieved successfully", demandsCount.orElseThrow()));
        return ResponseEntity.notFound().build();
    }




}


package com.jewel.ergon.jobs.controllers;

import com.jewel.ergon.jobs.model.Cv;
import com.jewel.ergon.jobs.services.CvService;
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


//TODO we fixed cv class to pass tests , so we should fix the other controllers exactly the same way
@RestController
@RequestMapping(value = "/api/v1/cvs", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Cv Controller", description = "API for managing cvs")
public class CvController {

    private final CvService cvService;

    @Autowired
    public CvController(CvService cvService) {
        this.cvService = cvService;
    }

    /**
     * Fetches all cvs for the user.
     */
    @SneakyThrows
    @Operation(summary = "Get all cvs", description = "Retrieve a list of all cvs for the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of cvs",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cv.class))})
    })
    @GetMapping("/getAllCvs")
    public ResponseEntity<StandardResponse<List<Cv>>> getAllCvs() {
        List<Cv> cvs = cvService.findAll();
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Cvs retrieved successfully", cvs));
    }

    /**
     * Fetches a cv by its unique identifier.
     */
    @Operation(summary = "Get cv by ID", description = "Retrieve a specific cv by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cv found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cv.class))}),
            @ApiResponse(responseCode = "404", description = "Cv not found", content = @Content)
    })
    @GetMapping("/getCvById/{id}")
    public ResponseEntity<StandardResponse<Cv>> getCvById(@PathVariable Long id) {
        Optional<Cv> cv = cvService.findById(id);
        if (cv.isPresent())
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Cv retrieved successfully", cv.orElseThrow()));
        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a new Cv.
     */
    @Operation(summary = "Create a new cv", description = "Add a new cv to the list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cv created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cv.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/createCv")
    public ResponseEntity<StandardResponse<Cv>> createCv(@Valid @RequestBody Cv cvRequest) {
        Cv createdCv = cvService.save(cvRequest);
        return new ResponseEntity<>(new StandardResponse<>(HttpStatus.CREATED.value(), "Cv created successfully", createdCv), HttpStatus.CREATED);
    }

    /**
     * Updates an existing Cv.
     */
    @SneakyThrows
    @Operation(summary = "Update an existing cv", description = "Update details of an existing cv by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cv updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cv.class))}),
            @ApiResponse(responseCode = "404", description = "Cv not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/updateCv/{id}")
    public ResponseEntity<StandardResponse<Cv>> updateCv(@PathVariable Long id, @Valid @RequestBody Cv cvRequest) {
        Cv updatedCv = cvService.save(cvRequest);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Cv updated successfully", updatedCv));
    }

    /**
     * Deletes a cv.
     */
    @Operation(summary = "Delete a cv", description = "Remove a cv from the list by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cv deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cv not found", content = @Content)
    })
    @DeleteMapping("/deleteCv/{id}")
    public ResponseEntity<StandardResponse<Cv>> deleteCv(@PathVariable Long id) {
        Optional<Cv> cv = cvService.findById(id);
        if (cv.isPresent()) {
            cvService.deleteById(id);
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.NO_CONTENT.value(), "Cv with id: %d is deleted".formatted(id), null));
        }
        return ResponseEntity.noContent().build();
    }
}


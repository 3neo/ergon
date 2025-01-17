package com.jewel.ergon.jobs.controllers;

import com.jewel.ergon.jobs.model.Cv;
import com.jewel.ergon.jobs.model.JobSeeker;
import com.jewel.ergon.jobs.services.CvService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/v1/cvs", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Cv Controller", description = "API for managing cvs")
public class CvController {

    private final CvService cvService;
    private static final Logger logger = LoggerFactory.getLogger(JobSeekerController.class);


    @Autowired
    public CvController(CvService cvService) {
        this.cvService = cvService;
    }


    /**
     * Fetches  Cvs using eql .
     */
    @SneakyThrows
    @Operation(summary = "Get all cvs using EQL", description = "Retrieve a list of desired cvs using eql")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of cvs",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cv.class))})
    })
    @GetMapping("/getAllCvsByEql")
    public ResponseEntity<StandardResponse<Page<Cv>>> getAllCompanies(@RequestParam(defaultValue = "") String query,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size,
                                                                      @RequestParam(defaultValue = "id,asc") String[] sort) {


        // Parsing sort parameter
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);
        // Creating pageable instance
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Cv> cvs = cvService.filter(query, Cv.class, pageable);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Cvs retrieved successfully", cvs));
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
    public ResponseEntity<StandardResponse<Page<Cv>>> getAllCvs(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(defaultValue = "id,asc") String[] sort) {
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);

        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Cv> cvs = cvService.findAll(pageable);
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
     * Creates a new CV.
     */
    @Operation(summary = "Create a new cv", description = "Add a new cv to the list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "cv created",
                    content = {@Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(implementation = Cv.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping(value = "/createCv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StandardResponse<Cv>> createJobSeeker(@RequestPart("entity") Cv cv,
                                                                @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {


        if (file != null) {
            logger.info("Received file: {}", file.getOriginalFilename());
            logger.info("Received file size: {}", file.getSize());
            logger.info("Received Content-Type: {}", file.getContentType());
            cv.setFile(file.getBytes());
        }
        Cv createdCv = cvService.save(cv);
        return new ResponseEntity<>(new StandardResponse<>(HttpStatus.CREATED.value(), "cv created successfully", createdCv), HttpStatus.CREATED);
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


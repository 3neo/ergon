package com.jewel.ergon.jobs.controllers;

import com.jewel.ergon.jobs.model.Experience;
import com.jewel.ergon.jobs.services.ExperienceService;
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
@RequestMapping(value = "/api/v1/experiences", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Experience Controller", description = "API for managing experiences")
public class ExperienceController {

    private final ExperienceService experienceService;

    @Autowired
    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }


    /**
     * Fetches  Experiences using eql .
     */
    @SneakyThrows
    @Operation(summary = "Get all experiences using EQL", description = "Retrieve a list of all experiences using eql")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of experiences",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Experience.class))})
    })
    @GetMapping("/getAllExperiencesByEql")
    public ResponseEntity<StandardResponse<Page<Experience>>> getAllCompanies(@RequestParam(defaultValue = "") String query,
                                                                              @RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size,
                                                                              @RequestParam(defaultValue = "id,asc") String[] sort) {


        // Parsing sort parameter
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);
        // Creating pageable instance
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Experience> experiences = experienceService.filter(query, Experience.class, pageable);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Experiences retrieved successfully", experiences));
    }







    /**
     * Fetches all experiences for the user.
     */
    @SneakyThrows
    @Operation(summary = "Get all experiences", description = "Retrieve a list of all experiences for the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of experiences",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Experience.class))})
    })
    @GetMapping("/getAllExperiences")
    public ResponseEntity<StandardResponse<Page<Experience>>> getAllExperiences(@RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                @RequestParam(defaultValue = "id,asc") String[] sort) {

        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);

        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Experience> experiences = experienceService.findAll(pageable);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Experiences retrieved successfully", experiences));
    }

    /**
     * Fetches a experience by its unique identifier.
     */
    @Operation(summary = "Get experience by ID", description = "Retrieve a specific experience by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Experience found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Experience.class))}),
            @ApiResponse(responseCode = "404", description = "Experience not found", content = @Content)
    })
    @GetMapping("/getExperienceById/{id}")
    public ResponseEntity<StandardResponse<Experience>> getExperienceById(@PathVariable Long id) {
        Optional<Experience> experience = experienceService.findById(id);
        if (experience.isPresent())
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Experience retrieved successfully", experience.orElseThrow()));
        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a new Experience.
     */
    @Operation(summary = "Create a new experience", description = "Add a new experience to the list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Experience created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Experience.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/createExperience")
    public ResponseEntity<StandardResponse<Experience>> createExperience(@Valid @RequestBody Experience experienceRequest) {
        Experience createdExperience = experienceService.save(experienceRequest);
        return new ResponseEntity<>(new StandardResponse<>(HttpStatus.CREATED.value(), "Experience created successfully", createdExperience), HttpStatus.CREATED);
    }

    /**
     * Updates an existing Experience.
     */
    @SneakyThrows
    @Operation(summary = "Update an existing experience", description = "Update details of an existing experience by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Experience updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Experience.class))}),
            @ApiResponse(responseCode = "404", description = "Experience not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/updateExperience/{id}")
    public ResponseEntity<StandardResponse<Experience>> updateExperience(@PathVariable Long id, @Valid @RequestBody Experience experienceRequest) {
        Experience updatedExperience = experienceService.save(experienceRequest);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Experience updated successfully", updatedExperience));
    }

    /**
     * Deletes a experience.
     */
    @Operation(summary = "Delete a experience", description = "Remove a experience from the list by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Experience deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Experience not found", content = @Content)
    })
    @DeleteMapping("/deleteExperience/{id}")
    public ResponseEntity<StandardResponse<Experience>> deleteExperience(@PathVariable Long id) {
        Optional<Experience> experience = experienceService.findById(id);
        if (experience.isPresent()) {
            experienceService.deleteById(id);
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.NO_CONTENT.value(), "Experience with id: %d is deleted".formatted(id), null));
        }
        return ResponseEntity.noContent().build();
    }
}


package com.jewel.ergon.jobs.controllers;

import com.jewel.ergon.jobs.model.Education;
import com.jewel.ergon.jobs.services.EducationService;
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


//TODO we fixed education class to pass tests , so we should fix the other controllers exactly the same way
@RestController
@RequestMapping(value = "/api/v1/educations", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Education Controller", description = "API for managing educations")
public class EducationController {

    private final EducationService educationService;

    @Autowired
    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    /**
     * Fetches all educations for the user.
     */
    @SneakyThrows
    @Operation(summary = "Get all educations", description = "Retrieve a list of all educations for the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of educations",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Education.class))})
    })
    @GetMapping("/getAllEducations")
    public ResponseEntity<StandardResponse<List<Education>>> getAllEducations() {
        List<Education> educations = educationService.findAll();
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Educations retrieved successfully", educations));
    }

    /**
     * Fetches a education by its unique identifier.
     */
    @Operation(summary = "Get education by ID", description = "Retrieve a specific education by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Education found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Education.class))}),
            @ApiResponse(responseCode = "404", description = "Education not found", content = @Content)
    })
    @GetMapping("/getEducationById/{id}")
    public ResponseEntity<StandardResponse<Education>> getEducationById(@PathVariable Long id) {
        Optional<Education> education = educationService.findById(id);
        if (education.isPresent())
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Education retrieved successfully", education.orElseThrow()));
        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a new Education.
     */
    @Operation(summary = "Create a new education", description = "Add a new education to the list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Education created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Education.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/createEducation")
    public ResponseEntity<StandardResponse<Education>> createEducation(@Valid @RequestBody Education educationRequest) {
        Education createdEducation = educationService.save(educationRequest);
        return new ResponseEntity<>(new StandardResponse<>(HttpStatus.CREATED.value(), "Education created successfully", createdEducation), HttpStatus.CREATED);
    }

    /**
     * Updates an existing Education.
     */
    @SneakyThrows
    @Operation(summary = "Update an existing education", description = "Update details of an existing education by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Education updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Education.class))}),
            @ApiResponse(responseCode = "404", description = "Education not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/updateEducation/{id}")
    public ResponseEntity<StandardResponse<Education>> updateEducation(@PathVariable Long id, @Valid @RequestBody Education educationRequest) {
        Education updatedEducation = educationService.save(educationRequest);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Education updated successfully", updatedEducation));
    }

    /**
     * Deletes a education.
     */
    @Operation(summary = "Delete a education", description = "Remove a education from the list by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Education deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Education not found", content = @Content)
    })
    @DeleteMapping("/deleteEducation/{id}")
    public ResponseEntity<StandardResponse<Education>> deleteEducation(@PathVariable Long id) {
        Optional<Education> education = educationService.findById(id);
        if (education.isPresent()) {
            educationService.deleteById(id);
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.NO_CONTENT.value(), "Education with id: %d is deleted".formatted(id), null));
        }
        return ResponseEntity.noContent().build();
    }
}


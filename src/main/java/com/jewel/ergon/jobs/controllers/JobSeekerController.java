package com.jewel.ergon.jobs.controllers;

import com.jewel.ergon.jobs.model.JobSeeker;
import com.jewel.ergon.jobs.services.JobSeekerService;
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


//TODO we fixed jobSeeker class to pass tests , so we should fix the other controllers exactly the same way
@RestController
@RequestMapping(value = "/api/v1/jobSeekers", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "JobSeeker Controller", description = "API for managing jobSeekers")
public class JobSeekerController {

    private final JobSeekerService jobSeekerService;

    @Autowired
    public JobSeekerController(JobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }

    /**
     * Fetches all jobSeekers for the user.
     */
    @SneakyThrows
    @Operation(summary = "Get all jobSeekers", description = "Retrieve a list of all jobSeekers for the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of jobSeekers",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JobSeeker.class))})
    })
    @GetMapping("/getAllJobSeekers")
    public ResponseEntity<StandardResponse<List<JobSeeker>>> getAllJobSeekers() {
        List<JobSeeker> jobSeekers = jobSeekerService.findAll();
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "JobSeekers retrieved successfully", jobSeekers));
    }

    /**
     * Fetches a jobSeeker by its unique identifier.
     */
    @Operation(summary = "Get jobSeeker by ID", description = "Retrieve a specific jobSeeker by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JobSeeker found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JobSeeker.class))}),
            @ApiResponse(responseCode = "404", description = "JobSeeker not found", content = @Content)
    })
    @GetMapping("/getJobSeekerById/{id}")
    public ResponseEntity<StandardResponse<JobSeeker>> getJobSeekerById(@PathVariable Long id) {
        Optional<JobSeeker> jobSeeker = jobSeekerService.findById(id);
        if (jobSeeker.isPresent())
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "JobSeeker retrieved successfully", jobSeeker.orElseThrow()));
        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a new JobSeeker.
     */
    @Operation(summary = "Create a new jobSeeker", description = "Add a new jobSeeker to the list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "JobSeeker created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JobSeeker.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/createJobSeeker")
    public ResponseEntity<StandardResponse<JobSeeker>> createJobSeeker(@Valid @RequestBody JobSeeker jobSeekerRequest) {
        JobSeeker createdJobSeeker = jobSeekerService.save(jobSeekerRequest);
        return new ResponseEntity<>(new StandardResponse<>(HttpStatus.CREATED.value(), "JobSeeker created successfully", createdJobSeeker), HttpStatus.CREATED);
    }

    /**
     * Updates an existing JobSeeker.
     */
    @SneakyThrows
    @Operation(summary = "Update an existing jobSeeker", description = "Update details of an existing jobSeeker by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JobSeeker updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JobSeeker.class))}),
            @ApiResponse(responseCode = "404", description = "JobSeeker not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/updateJobSeeker/{id}")
    public ResponseEntity<StandardResponse<JobSeeker>> updateJobSeeker(@PathVariable Long id, @Valid @RequestBody JobSeeker jobSeekerRequest) {
        JobSeeker updatedJobSeeker = jobSeekerService.save(jobSeekerRequest);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "JobSeeker updated successfully", updatedJobSeeker));
    }

    /**
     * Deletes a jobSeeker.
     */
    @Operation(summary = "Delete a jobSeeker", description = "Remove a jobSeeker from the list by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "JobSeeker deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "JobSeeker not found", content = @Content)
    })
    @DeleteMapping("/deleteJobSeeker/{id}")
    public ResponseEntity<StandardResponse<JobSeeker>> deleteJobSeeker(@PathVariable Long id) {
        Optional<JobSeeker> jobSeeker = jobSeekerService.findById(id);
        if (jobSeeker.isPresent()) {
            jobSeekerService.deleteById(id);
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.NO_CONTENT.value(), "JobSeeker with id: %d is deleted".formatted(id), null));
        }
        return ResponseEntity.noContent().build();
    }
}


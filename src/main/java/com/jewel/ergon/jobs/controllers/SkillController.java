package com.jewel.ergon.jobs.controllers;

import com.jewel.ergon.jobs.model.Skill;
import com.jewel.ergon.jobs.services.SkillService;
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


//TODO we fixed skill class to pass tests , so we should fix the other controllers exactly the same way
@RestController
@RequestMapping(value = "/api/v1/skills", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Skill Controller", description = "API for managing skills")
public class SkillController {

    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    /**
     * Fetches all skills for the user.
     */
    @SneakyThrows
    @Operation(summary = "Get all skills", description = "Retrieve a list of all skills for the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of skills",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Skill.class))})
    })
    @GetMapping("/getAllSkills")
    public ResponseEntity<StandardResponse<List<Skill>>> getAllSkills() {
        List<Skill> skills = skillService.findAll();
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Skills retrieved successfully", skills));
    }

    /**
     * Fetches a skill by its unique identifier.
     */
    @Operation(summary = "Get skill by ID", description = "Retrieve a specific skill by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skill found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Skill.class))}),
            @ApiResponse(responseCode = "404", description = "Skill not found", content = @Content)
    })
    @GetMapping("/getSkillById/{id}")
    public ResponseEntity<StandardResponse<Skill>> getSkillById(@PathVariable Long id) {
        Optional<Skill> skill = skillService.findById(id);
        if (skill.isPresent())
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Skill retrieved successfully", skill.orElseThrow()));
        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a new Skill.
     */
    @Operation(summary = "Create a new skill", description = "Add a new skill to the list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Skill created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Skill.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/createSkill")
    public ResponseEntity<StandardResponse<Skill>> createSkill(@Valid @RequestBody Skill skillRequest) {
        Skill createdSkill = skillService.save(skillRequest);
        return new ResponseEntity<>(new StandardResponse<>(HttpStatus.CREATED.value(), "Skill created successfully", createdSkill), HttpStatus.CREATED);
    }

    /**
     * Updates an existing Skill.
     */
    @SneakyThrows
    @Operation(summary = "Update an existing skill", description = "Update details of an existing skill by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skill updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Skill.class))}),
            @ApiResponse(responseCode = "404", description = "Skill not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/updateSkill/{id}")
    public ResponseEntity<StandardResponse<Skill>> updateSkill(@PathVariable Long id, @Valid @RequestBody Skill skillRequest) {
        Skill updatedSkill = skillService.save(skillRequest);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Skill updated successfully", updatedSkill));
    }

    /**
     * Deletes a skill.
     */
    @Operation(summary = "Delete a skill", description = "Remove a skill from the list by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Skill deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Skill not found", content = @Content)
    })
    @DeleteMapping("/deleteSkill/{id}")
    public ResponseEntity<StandardResponse<Skill>> deleteSkill(@PathVariable Long id) {
        Optional<Skill> skill = skillService.findById(id);
        if (skill.isPresent()) {
            skillService.deleteById(id);
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.NO_CONTENT.value(), "Skill with id: %d is deleted".formatted(id), null));
        }
        return ResponseEntity.noContent().build();
    }
}


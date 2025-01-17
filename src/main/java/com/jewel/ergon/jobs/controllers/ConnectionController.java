package com.jewel.ergon.jobs.controllers;

import com.jewel.ergon.jobs.model.Connection;
import com.jewel.ergon.jobs.services.ConnectionService;
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
@RequestMapping(value = "/api/v1/connections", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Connection Controller", description = "API for managing connections")
public class ConnectionController {

    private final ConnectionService connectionService;

    @Autowired
    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }


    //TODO add similar method to all controllers

    /**
     * Fetches  Connections using eql .
     */
    @SneakyThrows
    @Operation(summary = "Get all connections EQL", description = "Retrieve a list of all connections using eql")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of connections",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Connection.class))})
    })
    @GetMapping("/getAllConnectionsByEql")
    public ResponseEntity<StandardResponse<Page<Connection>>> getAllConnections(@RequestParam(defaultValue = "") String query,
                                                                                @RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                @RequestParam(defaultValue = "id,asc") String[] sort) {


        // Parsing sort parameter
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);
        // Creating pageable instance
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Connection> connections = connectionService.filter(query, Connection.class, pageable);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Connections retrieved successfully", connections));
    }


    /**
     * Fetches all connections for the user.
     */
    @SneakyThrows
    @Operation(summary = "Get all connections", description = "Retrieve a list of all connections for the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of connections",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Connection.class))})
    })
    @GetMapping("/getAllConnections")
    public ResponseEntity<StandardResponse<Page<Connection>>> getAllConnections(@RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                @RequestParam(defaultValue = "id,asc") String[] sort) {
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Connection> connections = connectionService.findAll(pageable);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Connections retrieved successfully", connections));
    }

    /**
     * Fetches a connection by its unique identifier.
     */
    @Operation(summary = "Get connection by ID", description = "Retrieve a specific connection by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connection found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Connection.class))}),
            @ApiResponse(responseCode = "404", description = "Connection not found", content = @Content)
    })
    @GetMapping("/getConnectionById/{id}")
    public ResponseEntity<StandardResponse<Connection>> getConnectionById(@PathVariable Long id) {
        Optional<Connection> connection = connectionService.findById(id);
        if (connection.isPresent())
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Connection retrieved successfully", connection.orElseThrow()));
        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a new Connection.
     */
    @Operation(summary = "Create a new connection", description = "Add a new connection to the list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Connection created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Connection.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/createConnection")
    public ResponseEntity<StandardResponse<Connection>> createConnection(@Valid @RequestBody Connection connectionRequest) {
        Connection createdConnection = connectionService.save(connectionRequest);
        return new ResponseEntity<>(new StandardResponse<>(HttpStatus.CREATED.value(), "Connection created successfully", createdConnection), HttpStatus.CREATED);
    }

    /**
     * Updates an existing Connection.
     */
    @SneakyThrows
    @Operation(summary = "Update an existing connection", description = "Update details of an existing connection by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connection updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Connection.class))}),
            @ApiResponse(responseCode = "404", description = "Connection not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/updateConnection/{id}")
    public ResponseEntity<StandardResponse<Connection>> updateConnection(@PathVariable Long id, @Valid @RequestBody Connection connectionRequest) {
        Connection updatedConnection = connectionService.save(connectionRequest);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Connection updated successfully", updatedConnection));
    }

    /**
     * Deletes a connection.
     */
    @Operation(summary = "Delete a connection", description = "Remove a connection from the list by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Connection deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Connection not found", content = @Content)
    })
    @DeleteMapping("/deleteConnection/{id}")
    public ResponseEntity<StandardResponse<Connection>> deleteConnection(@PathVariable Long id) {
        Optional<Connection> connection = connectionService.findById(id);
        if (connection.isPresent()) {
            connectionService.deleteById(id);
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.NO_CONTENT.value(), "Connection with id: %d is deleted".formatted(id), null));
        }
        return ResponseEntity.noContent().build();
    }
}


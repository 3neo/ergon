package com.jewel.ergon.jobs.controllers;

import com.jewel.ergon.jobs.model.Currency;
import com.jewel.ergon.jobs.services.CurrencyService;
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
@RequestMapping("/api/v1/currencies")
@Tag(name = "Currency Controller", description = "API for managing currencys")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * Fetches all currencys for the user.
     */
    @SneakyThrows
    @Operation(summary = "Get all currencys", description = "Retrieve a list of all currencys for the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of currencys",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Currency.class))})
    })
    @GetMapping("/getAllCurrencys")
    public ResponseEntity<StandardResponse<List<Currency>>> getAllCurrencys() {
        List<Currency> currencys = currencyService.findAll();
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Currencys retrieved successfully", currencys));
    }

    /**
     * Fetches a currency by its unique identifier.
     */
    @Operation(summary = "Get currency by ID", description = "Retrieve a specific currency by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Currency found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Currency.class))}),
            @ApiResponse(responseCode = "404", description = "Currency not found", content = @Content)
    })
    @GetMapping("/getCurrencyById/{id}")
    public ResponseEntity<StandardResponse<Currency>> getCurrencyById(@PathVariable Long id) {
        Optional<Currency> currency = currencyService.findById(id);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Currency retrieved successfully", currency.orElseThrow()));
    }

    /**
     * Creates a new Currency.
     */
    @Operation(summary = "Create a new currency", description = "Add a new currency to the list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Currency created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Currency.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/createCurrency")
    public ResponseEntity<StandardResponse<Currency>> createCurrency(@Valid @RequestBody Currency currencyRequest) {
        Currency createdCurrency = currencyService.save(currencyRequest);
        return new ResponseEntity<>(new StandardResponse<>(HttpStatus.CREATED.value(), "Currency created successfully", createdCurrency), HttpStatus.CREATED);
    }

    /**
     * Updates an existing Currency.
     */
    @SneakyThrows
    @Operation(summary = "Update an existing currency", description = "Update details of an existing currency by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Currency updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Currency.class))}),
            @ApiResponse(responseCode = "404", description = "Currency not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/updateCurrency/{id}")
    public ResponseEntity<StandardResponse<Currency>> updateCurrency(@PathVariable Long id, @Valid @RequestBody Currency currencyRequest) {
        Currency updatedCurrency = currencyService.save(currencyRequest);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Currency updated successfully", updatedCurrency));
    }

    /**
     * Deletes a currency.
     */
    @Operation(summary = "Delete a currency", description = "Remove a currency from the list by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Currency deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Currency not found", content = @Content)
    })
    @DeleteMapping("/deleteCurrency/{id}")
    public ResponseEntity<StandardResponse<Currency>> deleteCurrency(@PathVariable Long id) {
        currencyService.deleteById(id);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.NO_CONTENT.value(), "Currency with id: %d is deleted".formatted(id), null));
    }
}


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
@RequestMapping(value = "/api/v1/currencies", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Currency Controller", description = "API for managing currencys")
public class CurrencyController {

    private final CurrencyService currencyService;


    /**
     * Fetches  Currencys using eql .
     */
    @SneakyThrows
    @Operation(summary = "Get all currencies EQL", description = "Retrieve a list of all currencies using eql")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of currencies",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Currency.class))})
    })
    @GetMapping("/getAllCurrenciesByEql")
    public ResponseEntity<StandardResponse<Page<Currency>>> getAllCompanies(@RequestParam(defaultValue = "") String query,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size,
                                                                            @RequestParam(defaultValue = "id,asc") String[] sort) {


        // Parsing sort parameter
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);
        // Creating pageable instance
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Currency> currencies = currencyService.filter(query, Currency.class, pageable);
        return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Currencies retrieved successfully", currencies));
    }




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
    @GetMapping("/getAllCurrencies")
    public ResponseEntity<StandardResponse<Page<Currency>>> getAllCurrencies(@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size,
                                                                             @RequestParam(defaultValue = "id,asc") String[] sort) {

        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortOrder = Sort.by(direction, sort[0]);

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        Page<Currency> currencys = currencyService.findAll(pageable);
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
        if (currency.isPresent())
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.OK.value(), "Currency retrieved successfully", currency.orElseThrow()));
        return ResponseEntity.notFound().build();
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
        Optional<Currency> currency = currencyService.findById(id);
        if (currency.isPresent()) {
            currencyService.deleteById(id);
            return ResponseEntity.ok(new StandardResponse<>(HttpStatus.NO_CONTENT.value(), "Currency with id: %d is deleted".formatted(id), null));
        }
        return ResponseEntity.noContent().build();
    }
}


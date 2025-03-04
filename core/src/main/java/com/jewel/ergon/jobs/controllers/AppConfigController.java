package com.jewel.ergon.jobs.controllers;

import com.jewel.ergon.jobs.services.AppConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/v1/appConfig", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "AppConfig Controller", description = "API for managing app configuration")
public class AppConfigController {

    private final AppConfigService appConfigService;

    public AppConfigController(AppConfigService appConfigService) {
        this.appConfigService = appConfigService;
    }

    @GetMapping("/getTimezone")
    @Operation(summary = "Get the current timezone", description = "Retrieve the currently configured timezone.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved timezone",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "TimeZone not found", content = @Content)
    })
    public ResponseEntity<String> getCurrentTimeZone() {
        return appConfigService.getCurrentTimeZone()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Timezone not configured"));
    }


    @PostMapping("/updateTimezone")
    @Operation(summary = "Update the current timezone", description = "Update the currently configured timezone.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated timezone",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Failed to update TimeZone", content = @Content)
    })
    public ResponseEntity<String> updateTimeZone(@RequestParam String timeZoneId) {
        appConfigService.updateTimeZone(timeZoneId);
        return ResponseEntity.ok("Timezone updated successfully");
    }
}

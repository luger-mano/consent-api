package com.sensedia.consent.api.domain.controller;

import com.sensedia.consent.api.dto.req.ConsentCreatedRequestDto;
import com.sensedia.consent.api.dto.req.ConsentUpdatedRequestDto;
import com.sensedia.consent.api.dto.res.ConsentCreatedResponseDto;
import com.sensedia.consent.api.dto.res.ConsentPaginationDto;
import com.sensedia.consent.api.dto.res.ConsentResponseDto;
import com.sensedia.consent.api.service.consent.ConsentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/consents")
@RequiredArgsConstructor
@Tag(name = "Consent API", description = "Handle Consent APIs")
public class ConsentController {

    private final ConsentService consentService;


    @Operation(
            summary = "Create consent",
            description = "Creates a new consent resource in the system."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Consent created"),
                    @ApiResponse(responseCode = "204", description = "Consent created with empty resource"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized action"),
                    @ApiResponse(responseCode = "409", description = "There was a conflict in the action"),
                    @ApiResponse(responseCode = "500", description = "Throw an exception / Server side error")
            }
    )
    @PostMapping
    public ResponseEntity<Map<String, String>> createConsent(@RequestHeader("X-Idempotency-Key") String idempotencyKey,
                                                             @Valid @RequestBody ConsentCreatedRequestDto requestDto) {
        var response = consentService.createConsent(idempotencyKey, requestDto);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Get all consents",
            description = "Return a list of consent."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "List of consent return all data."),
                    @ApiResponse(responseCode = "204", description = "List with empty resource"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized action"),
                    @ApiResponse(responseCode = "409", description = "There was a conflict in the action"),
                    @ApiResponse(responseCode = "500", description = "Throw an exception / Server side error")
            }
    )
    @GetMapping
    public ResponseEntity<List<ConsentPaginationDto>> getAllConsents(@RequestParam(value = "page",
                                                                               defaultValue = "0") int page,
                                                                     @RequestParam(value = "pageSize",
                                                                           defaultValue = "10") int pageSize) {
        var response = consentService.getAllConsents(page, pageSize);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get Consent By Id",
            description = "Return a consent by id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Consent by id returned"),
                    @ApiResponse(responseCode = "204", description = "Consent returned with empty resource"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized action"),
                    @ApiResponse(responseCode = "409", description = "There was a conflict in the action"),
                    @ApiResponse(responseCode = "500", description = "Throw an exception / Server side error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ConsentResponseDto> getConsentById(@PathVariable UUID id) {
        var response = consentService.getConsentById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update Consent By Id",
            description = "Update a consent by id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Consent by id updated"),
                    @ApiResponse(responseCode = "204", description = "Consent updated with empty resource"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized action"),
                    @ApiResponse(responseCode = "409", description = "There was a conflict in the action"),
                    @ApiResponse(responseCode = "500", description = "Throw an exception / Server side error")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ConsentCreatedResponseDto> updateConsentById(@PathVariable UUID id,
                                                                       @Valid @RequestBody ConsentUpdatedRequestDto requestDto) {
        var response = consentService.updateConsentById(id, requestDto);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Delete Consent By Id",
            description = "Logical deletion of a consent resources by id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Consent by id deleted"),
                    @ApiResponse(responseCode = "204", description = "Consent deleted with empty resource"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized action"),
                    @ApiResponse(responseCode = "409", description = "There was a conflict in the action"),
                    @ApiResponse(responseCode = "500", description = "Throw an exception / Server side error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> revokeConsent(@PathVariable UUID id) {
        var response = consentService.deleteConsentById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get the change history",
            description = "Search for all changes that have been made to all methods"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Change History"),
                    @ApiResponse(responseCode = "204", description = "History empty"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized action"),
                    @ApiResponse(responseCode = "409", description = "There was a conflict in the action"),
                    @ApiResponse(responseCode = "500", description = "Throw an exception / Server side error")
            }
    )
    @GetMapping("/history")
    public ResponseEntity<Map<String, String>> getHistory() {
        var response = consentService.getHistory();
        return ResponseEntity.ok(response);
    }
}

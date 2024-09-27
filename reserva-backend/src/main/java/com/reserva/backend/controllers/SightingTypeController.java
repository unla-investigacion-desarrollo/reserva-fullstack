package com.reserva.backend.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reserva.backend.constants.SightingConstants;
import com.reserva.backend.dto.SightingTypeRequestDto;
import com.reserva.backend.services.ISightingTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/sighting/type")
public class SightingTypeController {

    @Autowired
    private ISightingTypeService sightingTypeService;

    @Operation(summary = "Crea un nuevo tipo de avistamiento, como Aves o Árboles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = SightingConstants.SIGHTINGTYPE_CREATE_SUCCESS, content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = SightingConstants.SIGHTINGTYPE_TAKEN, content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = SightingConstants.REQUEST_ERROR, content = @Content),
            @ApiResponse(responseCode = "500", description = SightingConstants.REQUEST_ERROR, content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
    public ResponseEntity<?> create(@Valid @RequestBody SightingTypeRequestDto request) {
        return new ResponseEntity<>(sightingTypeService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene un tipo de avistamiento por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SightingConstants.SIGHTINGTYPE_OK, content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = SightingConstants.SIGHTINGTYPE_NOT_FOUND, content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(sightingTypeService.getById(id));
    }

    @GetMapping("/getTiposAvistamientos")
    public ResponseEntity<?> getTiposAvistamientos() {
        return ResponseEntity.ok(sightingTypeService.getTiposAvistamientos());
    }

    @Operation(summary = "Actualiza un tipo de avistamiento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SightingConstants.SIGHTINGTYPE_UPDATE_SUCCESS, content = @Content),
            @ApiResponse(responseCode = "400", description = SightingConstants.SIGHTINGTYPE_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = "404", description = SightingConstants.SIGHTINGTYPE_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = "500", description = SightingConstants.REQUEST_ERROR, content = @Content)
    })
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
    public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody SightingTypeRequestDto request) {
        return ResponseEntity.ok(sightingTypeService.update(id, request));
    }

    @Operation(summary = "Elimina un tipo de avistamiento de manera lógica (borrado lógico)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SightingConstants.SIGHTINGTYPE_DELETE_SUCCESS, content = @Content),
            @ApiResponse(responseCode = "400", description = SightingConstants.SIGHTINGTYPE_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = "404", description = SightingConstants.SIGHTINGTYPE_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = "500", description = SightingConstants.REQUEST_ERROR, content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(sightingTypeService.delete(id));
    }

    @Operation(summary = "Restaura un tipo de avistamiento previamente eliminado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SightingConstants.SIGHTINGTYPE_RESTORE_SUCCESS, content = @Content),
            @ApiResponse(responseCode = "400", description = SightingConstants.SIGHTINGTYPE_IS_ACTIVE, content = @Content),
            @ApiResponse(responseCode = "404", description = SightingConstants.SIGHTINGTYPE_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = "500", description = SightingConstants.REQUEST_ERROR, content = @Content)
    })
    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
    public ResponseEntity<?> restore(@PathVariable("id") long id) {
        return ResponseEntity.ok(sightingTypeService.restore(id));
    }

    @Operation(summary = "Obtiene todos los tipos de avistamiento con paginación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SightingConstants.SIGHTINGTYPE_OK, content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = SightingConstants.SIGHTINGTYPE_RETRIEVE_ERROR, content = @Content(mediaType = "application/json")),
    })
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "category", defaultValue = "") String category,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "999999") int size,
            @RequestParam(value = "orderBy", defaultValue = "asc") String orderBy,
            @RequestParam(value = "sortBy", defaultValue = "id") String soryBy,
            @RequestParam(value = "active", defaultValue = "true") boolean active) {
        return ResponseEntity.ok(sightingTypeService.getAll(name, category, page, size, orderBy, soryBy, active));
    }

}

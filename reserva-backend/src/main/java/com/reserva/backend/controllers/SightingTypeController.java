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

import com.reserva.backend.dto.SightingTypeRequestDto;
import com.reserva.backend.services.ISightingTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/sighting/type")
public class SightingTypeController {

    @Autowired
    private ISightingTypeService sightingTypeService;

    @Operation(summary = "realiza la creacion de los tipos de avistamiento ej Aves, Arboles etc", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Ya existe un tipo_avistamiento con ese nombre", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "algo sali\u00F3 mal durante la solicitud", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
    public ResponseEntity<?> create(@Valid @RequestBody SightingTypeRequestDto request) {
        return new ResponseEntity<>(sightingTypeService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "trae un tipo de avistamiento por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "no hay ningun tipo_avistamiento con ese id", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(sightingTypeService.getById(id));
    }

    @Operation(summary = "realiza la edicion de los tipos de avistamiento", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content),
            @ApiResponse(responseCode = "400", description = "tipo_avistamiento no se encuentra activo", content = @Content),
            @ApiResponse(responseCode = "404", description = "no hay ningun tipo_avistamiento con ese id", content = @Content)
    })
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
    public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody SightingTypeRequestDto request) {
        return ResponseEntity.ok(sightingTypeService.update(id, request));
    }

    @Operation(summary = "realiza el borrado logico de los tipos de avistamiento", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content),
            @ApiResponse(responseCode = "400", description = "tipo_avistamiento ya se encuentra dado de baja", content = @Content),
            @ApiResponse(responseCode = "404", description = "no hay ningun tipo_avistamiento con ese id", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(sightingTypeService.delete(id));
    }

    @Operation(summary = "realiza el alta logico de los tipos de avistamiento borrado", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content),
            @ApiResponse(responseCode = "400", description = "tipo_avistamiento ya se encuentra dado de alta", content = @Content),
            @ApiResponse(responseCode = "404", description = "no hay ningun tipo_avistamiento con ese id", content = @Content)
    })
    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
    public ResponseEntity<?> restore(@PathVariable("id") long id) {
        return ResponseEntity.ok(sightingTypeService.restore(id));
    }

    @Operation(summary = "trae todos los tipos de avistamientos con paginacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "No se pudieron listar los tipo_avistamiento", content = @Content(mediaType = "application/json")),
    })
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "category", defaultValue = "") String category,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "999999") int size,
            @RequestParam(value = "orderBy", defaultValue = "asc") String orderBy,
            @RequestParam(value = "sortBy", defaultValue = "id") String soryBy) {
        return ResponseEntity.ok(sightingTypeService.getAll(name, category, page, size, orderBy, soryBy));
    }

}

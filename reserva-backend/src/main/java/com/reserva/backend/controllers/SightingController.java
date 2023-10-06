package com.reserva.backend.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reserva.backend.dto.SightingRequestDto;
import com.reserva.backend.dto.UpdateStatusDto;
import com.reserva.backend.services.ISightingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/sighting")
public class SightingController {
    
    @Autowired
    private ISightingService sightingService;

    @Operation(summary = "realiza la creacion completa de un avistamiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created", content = @Content),
            @ApiResponse(responseCode = "400", description = "el Tipo_Avistamiento no es valido", content = @Content),
            @ApiResponse(responseCode = "404", description = "el usuario no fue encontrado", content = @Content)
        })
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody SightingRequestDto request){
        return new ResponseEntity<>(sightingService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "trae el avistamiento dado su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "no hay ningun avistamiento con ese id", content = @Content(mediaType = "application/json"))
        })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(sightingService.getById(id));
    }

    @Operation(summary = "trae el avistamiento completo por idUsuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = "application/json")) 
    })
    @GetMapping("/getByUser/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable(name = "userId") long userId){
        return ResponseEntity.ok(sightingService.getByUserId(userId));
    }

    @Operation(summary = "trae todos los avistamientos con paginacion, filtros de estado y tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "no se pudieron listar los avistamientos", content = @Content(mediaType = "application/json")),
    })
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "status", defaultValue = "") String status,
			@RequestParam(value = "type", defaultValue = "") String type,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "999999") int size,
			@RequestParam(value = "orderBy", defaultValue = "asc") String orderBy,
			@RequestParam(value = "sortBy", defaultValue = "id") String sortBy){
        return ResponseEntity.ok(sightingService.getAll(status, type, page, size, orderBy, sortBy));
    }

    @Operation(summary = "realiza la actualizacion de el estado de los avistamientos", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content),
            @ApiResponse(responseCode = "404", description = "usuario o avistamiento no enecontrado", content = @Content)
    })
    @PostMapping("/status")
    @PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
    public ResponseEntity<?> updateStatus(@RequestBody UpdateStatusDto request){
        return ResponseEntity.ok(sightingService.updateStatus(request));
    }

}

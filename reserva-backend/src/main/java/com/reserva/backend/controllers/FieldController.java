package com.reserva.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reserva.backend.dto.FieldRequestDto;
import com.reserva.backend.services.IFieldService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/field")
public class FieldController {

    @Autowired
    private IFieldService fieldService;

    @Operation(summary = "realiza la creacion de un field para un avistamiento especifico", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created", content = @Content),
            @ApiResponse(responseCode = "400", description = "no se puede asignar un campo a un avistamiento no existente", content = @Content),
            @ApiResponse(responseCode = "417", description = "algo salió mal durante la solicitud", content = @Content)
        })
    @PostMapping("/sighting/{idSighting}/create")
    public ResponseEntity<?> create(@PathVariable("idSighting") long id, @RequestBody FieldRequestDto request){
        return new ResponseEntity<>(fieldService.create(id, request), HttpStatus.CREATED);
    }

    @Operation(summary = "trae el field dado su id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "not found field", content = @Content(mediaType = "application/json"))
        })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(fieldService.getById(id));
    }

    @Operation(summary = "actualiza un field activo", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "update ok", content = @Content),
			@ApiResponse(responseCode = "404", description = "not found field", content = @Content),
			@ApiResponse(responseCode = "417", description = "request failure", content = @Content) 
        })
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody FieldRequestDto request) {
		return ResponseEntity.ok(fieldService.update(id, request));
	}

    @Operation(summary = "realiza el borrado logico de un field", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "delete ok", content = @Content),
			@ApiResponse(responseCode = "404", description = "not foud field", content = @Content),
			@ApiResponse(responseCode = "417", description = "request failure", content = @Content) 
        })
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		return ResponseEntity.ok(fieldService.delete(id));
	}

    @Operation(summary = "realiza el alta logico de un field", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "restore ok", content = @Content),
			@ApiResponse(responseCode = "404", description = "foud field", content = @Content),
			@ApiResponse(responseCode = "417", description = "request failure", content = @Content) })
	@PatchMapping("/restore/{id}")
	public ResponseEntity<?> restore(@PathVariable("id") long id) {
		return ResponseEntity.ok(fieldService.restore(id));
	}

    @Operation(summary = "trae todos los field's que tiene un avistamiento", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "sighting not found", content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/sighting/{idSighting}/fields")
    public ResponseEntity<?> getAll(@PathVariable("idSighting") long id){
        return ResponseEntity.ok(fieldService.getBySightingId(id));
    }
    
}

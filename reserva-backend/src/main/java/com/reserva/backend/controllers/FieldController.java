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

import com.reserva.backend.constants.SightingConstants;
import com.reserva.backend.dto.FieldRequestDto;
import com.reserva.backend.services.IFieldService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/field")
public class FieldController {

    @Autowired
    private IFieldService fieldService;

    @Operation(summary = "Crea un campo para un avistamiento especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = SightingConstants.FIELD_CREATE_SUCCESS, content = @Content),
            @ApiResponse(responseCode = "400", description = SightingConstants.FIELD_ASSIGNMENT_ERROR, content = @Content),
            @ApiResponse(responseCode = "500", description = SightingConstants.FIELD_REQUEST_FAILURE, content = @Content)
        })
    @PostMapping("/sighting/{idSighting}/create")
    public ResponseEntity<?> create(@PathVariable("idSighting") long id, @RequestBody FieldRequestDto request){
        return new ResponseEntity<>(fieldService.create(id, request), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene un campo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SightingConstants.FIELD_OK, content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = SightingConstants.FIELD_NOT_FOUND, content = @Content(mediaType = "application/json"))
        })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(fieldService.getById(id));
    }

    @Operation(summary = "Actualiza un campo activo")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = SightingConstants.FIELD_UPDATE_SUCCESS, content = @Content),
			@ApiResponse(responseCode = "404", description = SightingConstants.FIELD_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = "500", description = SightingConstants.FIELD_REQUEST_FAILURE, content = @Content) 
        })
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody FieldRequestDto request) {
		return ResponseEntity.ok(fieldService.update(id, request));
	}

    @Operation(summary = "Elimina un campo de forma lógica")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = SightingConstants.FIELD_DELETE_SUCCESS, content = @Content),
			@ApiResponse(responseCode = "404", description = SightingConstants.FIELD_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = "500", description = SightingConstants.FIELD_REQUEST_FAILURE, content = @Content) 
        })
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		return ResponseEntity.ok(fieldService.delete(id));
	}

    @Operation(summary = "Restaura un campo eliminado")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = SightingConstants.FIELD_RESTORE_SUCCESS, content = @Content),
			@ApiResponse(responseCode = "404", description = SightingConstants.FIELD_IS_ACTIVE, content = @Content),
			@ApiResponse(responseCode = "500", description = SightingConstants.FIELD_REQUEST_FAILURE, content = @Content) })
	@PatchMapping("/restore/{id}")
	public ResponseEntity<?> restore(@PathVariable("id") long id) {
		return ResponseEntity.ok(fieldService.restore(id));
	}

    @Operation(summary = "Obtiene todos los campos de un avistamiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SightingConstants.FIELD_OK, content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = SightingConstants.FIELD_RETRIEVE_ERROR, content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/sighting/{idSighting}/fields")
    public ResponseEntity<?> getAll(@PathVariable("idSighting") long id){
        return ResponseEntity.ok(fieldService.getBySightingId(id));
    }
    
}

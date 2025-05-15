package com.reserva.backend.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.reserva.backend.constants.SightingConstants;
import com.reserva.backend.constants.StorageConstants;
import com.reserva.backend.constants.UserConstants;
import com.reserva.backend.dto.SightingRequestDto;
import com.reserva.backend.dto.SightingUpdateDto;
import com.reserva.backend.dto.UpdateStatusDto;
import com.reserva.backend.services.ISightingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/sighting")
public class SightingController {
    
    @Autowired
    private ISightingService sightingService;

    @Operation(summary = "Crea un nuevo avistamiento completo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = SightingConstants.SIGHTING_CREATE_SUCCESS, content = @Content),
            @ApiResponse(responseCode = "400", description = SightingConstants.SIGHTINGTYPE_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = "404", description = UserConstants.USER_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = "500", description = SightingConstants.REQUEST_ERROR, content = @Content)
        })
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestPart SightingRequestDto request, @RequestPart List<MultipartFile> files){
        if(files.size() > 3){
            return new ResponseEntity<>(StorageConstants.IMAGE_UPLOAD_MAX_LIMIT, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(sightingService.create(request, files), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene un avistamiento por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SightingConstants.SIGHTING_OK, content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = SightingConstants.SIGHTING_NOT_FOUND, content = @Content(mediaType = "application/json"))
        })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(sightingService.getById(id));
    }

    @Operation(summary = "Obtiene avistamientos completos asociados a un ID de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SightingConstants.SIGHTING_OK, content = @Content(mediaType = "application/json")) 
    })
    @GetMapping("/getByUser/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable(name = "userId") long userId){
        return ResponseEntity.ok(sightingService.getByUserId(userId));
    }

    
    @Operation(summary = "Obtiene avistamientos completos con paginación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SightingConstants.SIGHTING_OK, content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = SightingConstants.SIGHTING_RETRIEVE_ERROR, content = @Content(mediaType = "application/json")),
    })
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "status", defaultValue = "") String status,
			@RequestParam(value = "type", defaultValue = "") String type,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "999999") int size,
			@RequestParam(value = "orderBy", defaultValue = "asc") String orderBy,
			@RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "active", defaultValue = "1") boolean active){
        return ResponseEntity.ok(sightingService.getAll(name, status, type, page, size, orderBy, sortBy, active));
    }


    @Operation(summary = "Obtiene avistamientos para mapa")
    @GetMapping("/getAllForMap")
    public ResponseEntity<?> getAllForMap(){
        return ResponseEntity.ok(sightingService.getAllForMap());
    }

    @Operation(summary = "Actualiza el estado de los avistamientos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SightingConstants.SIGHTING_UPDATE_SUCCESS, content = @Content),
            @ApiResponse(responseCode = "404", description = SightingConstants.SIGHTING_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = "500", description = SightingConstants.REQUEST_ERROR, content = @Content)
    })
    @PostMapping("/status")
    @PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
    public ResponseEntity<?> updateStatus(@RequestBody UpdateStatusDto request){
        return ResponseEntity.ok(sightingService.updateStatus(request));
    }

    @Operation(summary = "Actualiza un avistamiento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SightingConstants.SIGHTING_UPDATE_SUCCESS, content = @Content),
            @ApiResponse(responseCode = "400", description = SightingConstants.SIGHTINGTYPE_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = "404", description = SightingConstants.SIGHTING_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = "500", description = SightingConstants.REQUEST_ERROR, content = @Content)
        })
    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody SightingUpdateDto request){
        return ResponseEntity.ok(sightingService.update(id, request));
    }
    
    @Operation(summary = "Elimina un avistamiento de manera lógica (borrado lógico)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SightingConstants.SIGHTING_DELETE_SUCCESS, content = @Content),
            @ApiResponse(responseCode = "404", description = SightingConstants.SIGHTING_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = "500", description = SightingConstants.REQUEST_ERROR, content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(sightingService.delete(id));
    }

    @Operation(summary = "Restaura un avistamiento previamente eliminado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SightingConstants.SIGHTING_RESTORE_SUCCESS, content = @Content),
            @ApiResponse(responseCode = "400", description = SightingConstants.SIGHTING_IS_ACTIVE, content = @Content),
            @ApiResponse(responseCode = "404", description = SightingConstants.SIGHTING_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = "500", description = SightingConstants.REQUEST_ERROR, content = @Content)
    })
    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
    public ResponseEntity<?> restore(@PathVariable("id") long id) {
        return ResponseEntity.ok(sightingService.restore(id));
    }

}

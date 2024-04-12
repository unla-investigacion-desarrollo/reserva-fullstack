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

import com.reserva.backend.dto.SightingRequestDto;
import com.reserva.backend.dto.SightingUpdateDto;
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
            @ApiResponse(responseCode = "404", description = "el usuario no fue encontrado", content = @Content),
            @ApiResponse(responseCode = "417", description = "Algo salió mal durante la solicitud", content = @Content)
        })
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestPart SightingRequestDto request, @RequestPart List<MultipartFile> files){
        if(files.size() > 3){
            return new ResponseEntity<>("No se puede subir mas de 3 imagenes por avistamiento", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(sightingService.create(request, files), HttpStatus.CREATED);
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
            @ApiResponse(responseCode = "404", description = "usuario o avistamiento no enecontrado", content = @Content),
            @ApiResponse(responseCode = "417", description = "Algo salió mal durante la solicitud", content = @Content)
    })
    @PostMapping("/status")
    @PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
    public ResponseEntity<?> updateStatus(@RequestBody UpdateStatusDto request){
        return ResponseEntity.ok(sightingService.updateStatus(request));
    }

    @Operation(summary = "realiza la actualizacion de un avistamiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "avistamiento actualizado correctamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "el Tipo_Avistamiento no es valido o no se puede actualizar un avistamiento que no te pertenece", content = @Content),
            @ApiResponse(responseCode = "404", description = "Avistamiento o usuario no encontrado", content = @Content),
            @ApiResponse(responseCode = "417", description = "Algo salió mal durante la solicitud", content = @Content)
        })
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody SightingUpdateDto request){
        return ResponseEntity.ok(sightingService.update(id, request));
    }
    
    @Operation(summary = "realiza el borrado logico de un avistamiento", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content),
            @ApiResponse(responseCode = "404", description = "no existe ese avistamiento", content = @Content),
            @ApiResponse(responseCode = "417", description = "Algo salió mal durante la solicitud", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(sightingService.delete(id));
    }

    @Operation(summary = "realiza el alta logico de un avistamiento borrado", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content),
            @ApiResponse(responseCode = "400", description = "avistamiento ya se encuentra dado de alta", content = @Content),
            @ApiResponse(responseCode = "404", description = "no existe ese avistamiento", content = @Content),
            @ApiResponse(responseCode = "417", description = "Algo salió mal durante la solicitud", content = @Content)
    })
    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
    public ResponseEntity<?> restore(@PathVariable("id") long id) {
        return ResponseEntity.ok(sightingService.restore(id));
    }

}

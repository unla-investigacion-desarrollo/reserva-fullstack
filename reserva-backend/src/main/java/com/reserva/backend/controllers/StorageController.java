package com.reserva.backend.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reserva.backend.services.IStorageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/storage")
@Tag(name = "Storage", description = "Gestión de imágenes de avistamientos")
public class StorageController {

    @Autowired
    private IStorageService storageService;

    @Operation(summary = "obtiene una imagen/recurso a partir de una url", security = @SecurityRequirement(name = "bearerAuth"))
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content),
            @ApiResponse(responseCode = "400", description = "La url no es valida", content = @Content),
            @ApiResponse(responseCode = "404", description = "No se pudo encontrar la imagen", content = @Content)
        })
    @GetMapping("/{url:.+}")
    public ResponseEntity<?> getImage(@PathVariable("url") String url){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
        .contentType(MediaType.IMAGE_JPEG)
        .body(storageService.getImage(url));
    }

    @Operation(summary = "obtiene una imagen/recurso a partir de un id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ok", content = @Content),
        @ApiResponse(responseCode = "400", description = "La url no es valida", content = @Content),
        @ApiResponse(responseCode = "404", description = "No se pudo encontrar la imagen", content = @Content)
    })
    @GetMapping("/image/{id}")
    public ResponseEntity<?> getImage(@PathVariable("id") long id){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
        .contentType(MediaType.IMAGE_JPEG)
        .body(storageService.getImage(id));
    }
    
    @Operation(summary = "elimina una imagen (fisicamente) NO logicamente, 'Si eliminas las imagenes de un avistamiento no vas a poder reestablecer ese avistamiento'", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ok", content = @Content),
        @ApiResponse(responseCode = "400", description = "Error al intentar eliminar la imagen", content = @Content),
        @ApiResponse(responseCode = "404", description = "No existe una imagen con ese id", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        return ResponseEntity.ok(storageService.delete(id));
    }

    @Operation(summary = "Redirige a la imagen original")
    @GetMapping("/redirect/{url:.+}")
    public ResponseEntity<Void> redirectToImage(@PathVariable String url) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }

    @Operation(summary = "Obtener imágenes por ID de avistamiento", description = "Devuelve las URLs de las imágenes asociadas a un avistamiento específico", responses = {
        @ApiResponse(responseCode = "200", description = "URLs de imágenes encontradas", 
        content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = String[].class))),
        @ApiResponse(responseCode = "404", description = "Avistamiento no encontrado")
    })
    
    @GetMapping("/by-sighting/{sightingId}")
    public ResponseEntity<List<String>> getImagesBySighting(
            @Parameter(description = "ID del avistamiento", required = true, example = "17" // 👈 Ejemplo de valor
            ) @PathVariable Long sightingId) {
        List<String> urls = storageService.getImageUrlsBySightingId(sightingId);
        return ResponseEntity.ok(urls);
    }
}

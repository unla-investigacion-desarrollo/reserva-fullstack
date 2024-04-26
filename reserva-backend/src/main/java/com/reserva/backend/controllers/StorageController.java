package com.reserva.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reserva.backend.services.IStorageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private IStorageService storageService;

    @Operation(summary = "obtiene una imagen/recurso a partir de una url")
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

    @Operation(summary = "obtiene una imagen/recurso a partir de un id")
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
    
    @Operation(summary = "elimina una imagen (fisicamente) NO logicamente, 'Si eliminas las imagenes de un avistamiento no vas a poder reestablecer ese avistamiento'")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ok", content = @Content),
        @ApiResponse(responseCode = "400", description = "Error al intentar eliminar la imagen", content = @Content),
        @ApiResponse(responseCode = "404", description = "No existe una imagen con ese id", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        return ResponseEntity.ok(storageService.delete(id));
    }
}

package com.reserva.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reserva.backend.constants.StorageConstants;
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

    @Operation(summary = "Obtiene una imagen o recurso a partir de una URL")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = StorageConstants.STORAGE_OK, content = @Content),
            @ApiResponse(responseCode = "400", description = StorageConstants.IMAGE_URL_INVALID, content = @Content),
            @ApiResponse(responseCode = "404", description = StorageConstants.IMAGE_NOT_FOUND, content = @Content)
        })
    @GetMapping("/{url:.+}")
    public ResponseEntity<?> getImage(@PathVariable("url") String url){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
        .contentType(MediaType.IMAGE_JPEG)
        .body(storageService.getImage(url));
    }

    @Operation(summary = "Obtiene una imagen o recurso a partir de un ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = StorageConstants.STORAGE_OK, content = @Content),
        @ApiResponse(responseCode = "400", description = StorageConstants.IMAGE_URL_INVALID, content = @Content),
        @ApiResponse(responseCode = "404", description = StorageConstants.IMAGE_NOT_FOUND, content = @Content)
    })
    @GetMapping("/image/{id}")
    public ResponseEntity<?> getImage(@PathVariable("id") long id){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
        .contentType(MediaType.IMAGE_JPEG)
        .body(storageService.getImage(id));
    }
    
    @Operation(summary = "Elimina una imagen de forma permanente (no lógicamente)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = StorageConstants.IMAGE_DELETE_SUCCESS, content = @Content),
        @ApiResponse(responseCode = "400", description = StorageConstants.IMAGE_DELETE_ERROR, content = @Content),
        @ApiResponse(responseCode = "404", description = StorageConstants.IMAGE_NOT_FOUND, content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        return ResponseEntity.ok(storageService.delete(id));
    }
}

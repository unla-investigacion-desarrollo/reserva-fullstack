package com.reserva.backend.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reserva.backend.dto.SightingRequestDto;
import com.reserva.backend.services.ISightingService;

@RestController
@RequestMapping("/api/sighting")
public class SightingController {
    
    @Autowired
    private ISightingService sightingService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody SightingRequestDto request){
        return new ResponseEntity<>(sightingService.create(request), HttpStatus.CREATED);
    }
}

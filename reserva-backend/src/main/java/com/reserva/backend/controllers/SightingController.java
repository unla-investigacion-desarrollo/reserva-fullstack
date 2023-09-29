package com.reserva.backend.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reserva.backend.dto.SightingRequestDto;
import com.reserva.backend.services.ISightingService;

@RestController
@RequestMapping("/sighting")
public class SightingController {
    
    @Autowired
    private ISightingService sightingService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody SightingRequestDto request){
        return new ResponseEntity<>(sightingService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(sightingService.getById(id));
    }

    @GetMapping("/getByUser/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable(name = "userId") long userId){
        return ResponseEntity.ok(sightingService.getByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "status", defaultValue = "") String status,
			@RequestParam(value = "type", defaultValue = "") String type,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "999999") int size,
			@RequestParam(value = "orderBy", defaultValue = "asc") String orderBy,
			@RequestParam(value = "sortBy", defaultValue = "id") String sortBy){
        return ResponseEntity.ok(sightingService.getAll(status, type, page, size, orderBy, sortBy));
    }

}

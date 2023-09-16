package com.reserva.backend.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.reserva.backend.dto.user.UserRequestDto;
import com.reserva.backend.dto.user.UserUpdateDto;
import com.reserva.backend.services.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
public class UserController {

	@Autowired
	private IUserService userService;

	@Operation(summary = "realiza el alta manual de un usuario o personal de la reserva", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "usuario creado correctamente", content = @Content),
			@ApiResponse(responseCode = "400", description = "email o username existentes", content = @Content),
			@ApiResponse(responseCode = "404", description = "no existe un rol con ese nombre", content = @Content),
			@ApiResponse(responseCode = "417", description = "algo salió mal en el mapeo", content = @Content) })
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody UserRequestDto request) {
		return ResponseEntity.ok(userService.create(request));
	}

	@Operation(summary = "trae un usuario por su id", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "no se encontro ningun usuario con ese id", content = @Content) })
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") long id) {
		return ResponseEntity.ok(userService.getById(id));
	}

	@Operation(summary = "actualiza un usuario activo", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "usuario actualizado correctamente", content = @Content),
			@ApiResponse(responseCode = "400", description = "email o username existentes o usuario inactivo", content = @Content),
			@ApiResponse(responseCode = "401", description = "los id's no coinciden, no puedes utilizar a este recurso", content = @Content),
			@ApiResponse(responseCode = "404", description = "no existe un rol con ese nombre", content = @Content) })
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody UserUpdateDto request) {
		return ResponseEntity.ok(userService.update(id, request));
	}

	@Operation(summary = "realiza el borrado logico de un usuario", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "usuario dado de baja correctamente", content = @Content),
			@ApiResponse(responseCode = "400", description = "el usuario ya se encuentra dado de baja", content = @Content),
			@ApiResponse(responseCode = "404", description = "usuario no encontrado", content = @Content) })
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		return ResponseEntity.ok(userService.delete(id));
	}

	@Operation(summary = "realiza el alta logico de un usuario", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "usuario dado de alta correctamente", content = @Content),
			@ApiResponse(responseCode = "400", description = "el usuario ya se encuentra dado de alta", content = @Content),
			@ApiResponse(responseCode = "404", description = "usuario no encontrado", content = @Content) })
	@PatchMapping("/restore/{id}")
	public ResponseEntity<?> restore(@PathVariable("id") long id) {
		return ResponseEntity.ok(userService.restore(id));
	}
	
	@Operation(summary = "trae a todos los usarios y se implementa pageable", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "417", description = "no se pudieron listar los usuarios", content = @Content) })
	@GetMapping
	public ResponseEntity<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "999999") int size,
			@RequestParam(value = "orderBy", defaultValue = "asc") String orderBy,
			@RequestParam(value = "sortBy", defaultValue = "id") String soryBy) {
		return ResponseEntity.ok(userService.getAll(name, page, size, orderBy, soryBy));
	}

}

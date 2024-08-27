package com.reserva.backend.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.reserva.backend.constants.AuthConstants;
import com.reserva.backend.constants.UserConstants;
import com.reserva.backend.dto.user.UserRequestDto;
import com.reserva.backend.dto.user.UserUpdateDto;
import com.reserva.backend.services.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ROLE_PERSONAL_RESERVA')")
public class UserController {

	@Autowired
	private IUserService userService;

	@Operation(summary = "Realiza el alta manual de un usuario o miembro del personal de la reserva")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = UserConstants.USER_CREATE_SUCCESS, content = @Content),
			@ApiResponse(responseCode = "400", description = AuthConstants.USERNAME_TAKEN, content = @Content),
			@ApiResponse(responseCode = "404", description = UserConstants.ROLE_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = "500", description = UserConstants.REQUEST_ERROR, content = @Content) })
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody UserRequestDto request) {
		return new ResponseEntity<>(userService.create(request), HttpStatus.CREATED);
	}

	@Operation(summary = "Obtiene un usuario por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = UserConstants.USER_OK, content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = UserConstants.USER_NOT_FOUND, content = @Content) })
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") long id) {
		return ResponseEntity.ok(userService.getById(id));
	}

	@Operation(summary = "Actualiza un usuario existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = UserConstants.USER_UPDATE_SUCCESS, content = @Content),
			@ApiResponse(responseCode = "400", description = AuthConstants.USERNAME_TAKEN, content = @Content),
			@ApiResponse(responseCode = "401", description = UserConstants.RESOURCE_ID_MISMATCH, content = @Content),
			@ApiResponse(responseCode = "404", description = UserConstants.ROLE_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = "500", description = UserConstants.REQUEST_ERROR, content = @Content) })
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody UserUpdateDto request) {
		return ResponseEntity.ok(userService.update(id, request));
	}

	@Operation(summary = "Elimina un usuario de manera lógica (borrado lógico)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = UserConstants.USER_DELETE_SUCCESS, content = @Content),
			@ApiResponse(responseCode = "400", description = UserConstants.USER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = "404", description = UserConstants.USER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = "500", description = UserConstants.REQUEST_ERROR, content = @Content) })
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		return ResponseEntity.ok(userService.delete(id));
	}

	@Operation(summary = "Restaura un usuario previamente eliminado")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = UserConstants.USER_RESTORE_SUCCESS, content = @Content),
			@ApiResponse(responseCode = "400", description = UserConstants.USER_ACTIVE, content = @Content),
			@ApiResponse(responseCode = "404", description = UserConstants.USER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = "500", description = UserConstants.REQUEST_ERROR, content = @Content) })
	@PatchMapping("/restore/{id}")
	public ResponseEntity<?> restore(@PathVariable("id") long id) {
		return ResponseEntity.ok(userService.restore(id));
	}
	
	@Operation(summary = "Obtiene todos los usuarios con paginación")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = UserConstants.USER_OK, content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = UserConstants.USER_RETRIEVE_ERROR, content = @Content) })
	@GetMapping
	public ResponseEntity<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "999999") int size,
			@RequestParam(value = "orderBy", defaultValue = "asc") String orderBy,
			@RequestParam(value = "sortBy", defaultValue = "id") String soryBy) {
		return ResponseEntity.ok(userService.getAll(name, page, size, orderBy, soryBy));
	}

}

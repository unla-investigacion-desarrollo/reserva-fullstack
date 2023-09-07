package com.reserva.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SightingTypeRequestDto {
	
	private long id;
	@NotBlank(message = "el nombre no debe estar vacio")
	@Size(max = 250, message = "el nombre no debe tener más de {max} caracteres")
	private String name;
	@NotBlank(message = "no se puede una categoria vacia")
	@Pattern(regexp = "Flora|Fauna", message = "Solo existen dos categorias 'Flora' y 'Fauna'")
	private String category;
	//private boolean active;

}

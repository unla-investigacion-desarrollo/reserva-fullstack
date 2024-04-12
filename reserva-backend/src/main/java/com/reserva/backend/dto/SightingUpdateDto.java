package com.reserva.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SightingUpdateDto {

    private long userId;
    @NotBlank(message = "el nombre no debe estar vacio")
	@Size(max = 250, message = "el nombre no debe tener más de {max} caracteres")
    private String name;
    @NotBlank(message = "el nombre cientifico no debe estar vacio")
	@Size(max = 250, message = "el nombre cientifico no debe tener más de {max} caracteres")
    private String scientificName;
    private double latitude;
    private double longitude;
    @NotBlank(message = "el tipo es obligatorio")
    private String type;
    
}

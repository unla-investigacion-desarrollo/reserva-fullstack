package com.reserva.backend.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SightingCustomResponseDto {

    
    private int currentPage;
    private int amountOfPages;
    private List<SightingResponseDto> sightings;
}

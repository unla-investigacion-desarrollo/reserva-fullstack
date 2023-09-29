package com.reserva.backend.dto;

import java.util.Date;
import java.util.List;

import com.reserva.backend.dto.user.UserResponseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SightingResponseDto {

    private long id;
    private String name;
    private String ScientificName;
    private Date createdAt;
    private double latitude;
    private double longitude;
    private SightingTypeResponseDto type;
    private UserResponseDto createdBy;
    private List<FieldRequestDto> fields;

    
}

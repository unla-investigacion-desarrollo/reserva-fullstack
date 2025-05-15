package com.reserva.backend.dto;

import java.util.Date;
import java.util.List;

import com.reserva.backend.dto.user.UserResponseDto;
import com.reserva.backend.entities.Field;
import com.reserva.backend.entities.Image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SightingMapResponseDto {

    private long id;

    private double latitude;

    private double longitude;

    private String status;

    private List<Field> fields;

}

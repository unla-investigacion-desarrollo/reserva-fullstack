package com.reserva.backend.services;

import java.util.List;

import com.reserva.backend.dto.FieldRequestDto;
import com.reserva.backend.util.Responses;

public interface IFieldService {

    public Responses<FieldRequestDto> create(long sightingId, FieldRequestDto request);
    public FieldRequestDto getById(long id);
    public Responses<FieldRequestDto> update(long id, FieldRequestDto request);
    public Responses<FieldRequestDto> delete(long id);
    public Responses<FieldRequestDto> restore(long id);
    public List<FieldRequestDto> getBySightingId(long sightingId);
    
}

package com.reserva.backend.services;

import java.util.List;

import com.reserva.backend.dto.SightingRequestDto;
import com.reserva.backend.dto.SightingResponseDto;

public interface ISightingService {
    
    public boolean create(SightingRequestDto request);
    public SightingResponseDto getById(long id);
    public List<SightingResponseDto> getByUserId(long id);
    public List<SightingResponseDto> getAll(String status, String type, int page, int size, String orderBy, String sortBy);
}

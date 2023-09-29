package com.reserva.backend.services;

import com.reserva.backend.dto.SightingRequestDto;
import com.reserva.backend.dto.SightingResponseDto;

public interface ISightingService {
    
    public boolean create(SightingRequestDto request);
    public SightingResponseDto getById(long id);
    
}

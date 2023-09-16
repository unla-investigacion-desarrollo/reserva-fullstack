package com.reserva.backend.services;

import com.reserva.backend.dto.SightingRequestDto;

public interface ISightingService {
    
    public boolean create(SightingRequestDto request);
    
}

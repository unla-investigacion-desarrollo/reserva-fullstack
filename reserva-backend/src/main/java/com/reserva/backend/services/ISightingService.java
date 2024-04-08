package com.reserva.backend.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.reserva.backend.dto.SightingCustomResponseDto;
import com.reserva.backend.dto.SightingRequestDto;
import com.reserva.backend.dto.SightingResponseDto;
import com.reserva.backend.dto.UpdateStatusDto;
import com.reserva.backend.util.Responses;

public interface ISightingService {
    
    public Responses<SightingResponseDto> create(SightingRequestDto request, List<MultipartFile> files);
    public SightingResponseDto getById(long id);
    public List<SightingResponseDto> getByUserId(long id);
    public SightingCustomResponseDto getAll(String status, String type, int page, int size, String orderBy, String sortBy);
    public Responses<SightingResponseDto> updateStatus(UpdateStatusDto request);
    
}

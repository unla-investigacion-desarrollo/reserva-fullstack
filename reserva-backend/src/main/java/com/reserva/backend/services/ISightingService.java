package com.reserva.backend.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.reserva.backend.dto.SightingRequestDto;
import com.reserva.backend.dto.SightingResponseDto;
import com.reserva.backend.dto.UpdateStatusDto;

public interface ISightingService {
    
    public SightingResponseDto create(SightingRequestDto request, List<MultipartFile> files);
    public SightingResponseDto getById(long id);
    public List<SightingResponseDto> getByUserId(long id);
    public List<SightingResponseDto> getAll(String status, String type, int page, int size, String orderBy, String sortBy);
    public String updateStatus(UpdateStatusDto request);
}

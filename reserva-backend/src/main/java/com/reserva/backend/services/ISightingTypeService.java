package com.reserva.backend.services;

import com.reserva.backend.dto.SightingTypeRequestDto;
import com.reserva.backend.dto.SightingTypeResponseDto;
import com.reserva.backend.util.ResponsePageable;
import com.reserva.backend.util.Responses;

public interface ISightingTypeService {
	
	public Responses<SightingTypeResponseDto> create(SightingTypeRequestDto request);
	public SightingTypeResponseDto getById(long id);
	public Responses<SightingTypeResponseDto> update(long id, SightingTypeRequestDto request);
	public Responses<SightingTypeResponseDto> delete(long id);
	public Responses<SightingTypeResponseDto> restore(long id);
	public ResponsePageable<SightingTypeResponseDto> getAll(String name, String category, int page, int size, String orderBy, String sortBy);

}

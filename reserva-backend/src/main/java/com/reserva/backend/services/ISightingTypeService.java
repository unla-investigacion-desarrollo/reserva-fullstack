package com.reserva.backend.services;

import java.util.List;

import com.reserva.backend.dto.SightingTypeRequestDto;
import com.reserva.backend.dto.SightingTypeResponseDto;

public interface ISightingTypeService {
	
	public SightingTypeResponseDto create(SightingTypeRequestDto request);
	public SightingTypeResponseDto getById(long id);
	public String update(long id);
	public String delete(long id);
	public String restore(long id);
	public List<SightingTypeResponseDto> getAll(String name, String category, int page, int size, String orderBy, String sortBy);

}

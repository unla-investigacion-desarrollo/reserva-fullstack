package com.reserva.backend.services.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.hibernate.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.reserva.backend.constants.SightingConstants;
import com.reserva.backend.dto.SightingTypeRequestDto;
import com.reserva.backend.dto.SightingTypeResponseDto;
import com.reserva.backend.entities.SightingType;
import com.reserva.backend.repositorys.ISightingTypeRepository;
import com.reserva.backend.services.ISightingTypeService;
import com.reserva.backend.exceptions.ReservaException;

@Service
public class SightingTypeService implements ISightingTypeService{
	
	@Autowired
	private ISightingTypeRepository sightingTypeRepository;
	
	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public SightingTypeResponseDto create(SightingTypeRequestDto request) {
		if(sightingTypeRepository.existsByName(request.getName())) {
			throw new ReservaException(SightingConstants.SIGHTINGTYPE_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
		}
		try {
			SightingType tipo = modelMapper.map(request, SightingType.class);
			tipo.setActive(true);
			sightingTypeRepository.save(tipo);
			SightingTypeResponseDto response = modelMapper.map(tipo, SightingTypeResponseDto.class);
			return response;
		}catch(MappingException e) {
			throw new ReservaException(SightingConstants.MAPPING_WRONG, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public SightingTypeResponseDto getById(long id) {
		Optional<SightingType> tipo = sightingTypeRepository.findById(id);
		if(!tipo.isPresent()) {
			throw new ReservaException(SightingConstants.SIGHTINGTYPE_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		SightingTypeResponseDto response = modelMapper.map(tipo.get(), SightingTypeResponseDto.class);
		return response;
	}

	@Override
	public String update(long id, SightingTypeRequestDto request) {
		Optional<SightingType> tipo = sightingTypeRepository.findById(id);
		if(!tipo.isPresent()) {
			throw new ReservaException(SightingConstants.SIGHTINGTYPE_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		if(!tipo.get().isActive()) {
			throw new ReservaException(SightingConstants.SIGHTINGTYPE_NOT_FOUND, HttpStatus.BAD_REQUEST);
		}
		SightingType update = tipo.get();
		update.setName(request.getName());
		update.setCategory(request.getCategory());
		sightingTypeRepository.save(update);
		return SightingConstants.SIGHTINGTYPE_UPDATE_SUCCESSFUL;
	}

	@Override
	public String delete(long id) {
		Optional<SightingType> tipo = sightingTypeRepository.findById(id);
		if(!tipo.isPresent()) {
			throw new ReservaException(SightingConstants.SIGHTINGTYPE_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		if(!tipo.get().isActive()) {
			throw new ReservaException(SightingConstants.SIGHTINGTYPE_NOT_FOUND, HttpStatus.BAD_REQUEST);
		}
		tipo.get().setActive(false);
		sightingTypeRepository.save(tipo.get());
		return SightingConstants.SIGHTINGTYPE_DELETE_SUCCESSFUL;
	}

	@Override
	public String restore(long id) {
		Optional<SightingType> tipo = sightingTypeRepository.findById(id);
		if(!tipo.isPresent()) {
			throw new ReservaException(SightingConstants.SIGHTINGTYPE_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		if(tipo.get().isActive()) {
			throw new ReservaException(SightingConstants.SIGHTINGTYPE_IS_ACTIVE, HttpStatus.BAD_REQUEST);
		}
		tipo.get().setActive(true);
		sightingTypeRepository.save(tipo.get());
		return SightingConstants.SIGHTINGTYPE_IS_ACTIVE;
	}

	@Override
	public List<SightingTypeResponseDto> getAll(String name, String category, int page, int size, String orderBy, String sortBy) {
		try {
			if(page < 1) page = 1; if(size < 1) size = 999999;
			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(orderBy.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy.toLowerCase()));
			Page<SightingType> pageTipo;
			if(!name.isEmpty()) {
				pageTipo = sightingTypeRepository.findByNameContaining(name, pageable);
			}else if(!category.isEmpty()){
				pageTipo = sightingTypeRepository.findByCategory(category, pageable);
			}else{
				pageTipo = sightingTypeRepository.findAll(pageable);
			}
			List<SightingTypeResponseDto> response = new ArrayList<>();
			for(SightingType t : pageTipo.getContent()) {
				response.add(modelMapper.map(t, SightingTypeResponseDto.class));
			}
			return response;
			}catch(Exception e) {
			throw new ReservaException(SightingConstants.SIGHTINGTYPE_LIST_ERROR, HttpStatus.EXPECTATION_FAILED);
		}
	}

}

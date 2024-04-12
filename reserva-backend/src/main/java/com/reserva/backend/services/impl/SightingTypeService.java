package com.reserva.backend.services.impl;

import java.util.stream.Collectors;

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
import com.reserva.backend.util.ResponsePageable;
import com.reserva.backend.util.Responses;
import com.reserva.backend.exceptions.ReservaException;

@Service
public class SightingTypeService implements ISightingTypeService{
	
	@Autowired
	private ISightingTypeRepository sightingTypeRepository;
	
	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public Responses<SightingTypeResponseDto> create(SightingTypeRequestDto request) {
		if (sightingTypeRepository.existsByName(request.getName())) {
			throw new ReservaException(SightingConstants.SIGHTINGTYPE_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
		}
		try {
			SightingType tipo = modelMapper.map(request, SightingType.class);
			tipo.setActive(true);
			sightingTypeRepository.save(tipo);
			SightingTypeResponseDto response = modelMapper.map(tipo, SightingTypeResponseDto.class);
			return new Responses<>(true, SightingConstants.SIGHTINGTYPE_CREATED, response);
		} catch (Exception e) {
			throw new ReservaException(SightingConstants.REQUEST_FAILURE, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public SightingTypeResponseDto getById(long id) {
		SightingType tipo = sightingTypeRepository.findById(id).orElseThrow(
				() -> new ReservaException(SightingConstants.SIGHTINGTYPE_NOT_FOUND, HttpStatus.NOT_FOUND));
		SightingTypeResponseDto response = modelMapper.map(tipo, SightingTypeResponseDto.class);
		return response;
	}

	@Override
	public Responses<SightingTypeResponseDto> update(long id, SightingTypeRequestDto request) {
		SightingType tipo = sightingTypeRepository.findById(id)
			.orElseThrow(() -> new ReservaException(SightingConstants.SIGHTINGTYPE_NOT_FOUND, HttpStatus.NOT_FOUND));
		if (!tipo.isActive()) {
			throw new ReservaException(SightingConstants.SIGHTINGTYPE_NOT_FOUND, HttpStatus.BAD_REQUEST);
		}
		try {
			SightingType update = tipo;
			update.setName(request.getName());
			update.setCategory(request.getCategory());
			sightingTypeRepository.save(update);
			return new Responses<>(true, SightingConstants.SIGHTINGTYPE_UPDATE_SUCCESSFUL, getById(id));
		} catch (Exception e) {
			throw new ReservaException(SightingConstants.REQUEST_FAILURE, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public Responses<SightingTypeResponseDto> delete(long id) {
		SightingType tipo = sightingTypeRepository.findById(id)
			.orElseThrow(() -> new ReservaException(SightingConstants.SIGHTINGTYPE_NOT_FOUND, HttpStatus.NOT_FOUND));
		if (!tipo.isActive()) {
			throw new ReservaException(SightingConstants.SIGHTINGTYPE_NOT_FOUND, HttpStatus.BAD_REQUEST);
		}
		try {
			tipo.setActive(false);
			sightingTypeRepository.save(tipo);
			return new Responses<>(true, SightingConstants.SIGHTINGTYPE_DELETE_SUCCESSFUL, getById(id));
		} catch (Exception e) {
			throw new ReservaException(SightingConstants.REQUEST_FAILURE, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public Responses<SightingTypeResponseDto> restore(long id) {
		SightingType tipo = sightingTypeRepository.findById(id)
			.orElseThrow(() -> new ReservaException(SightingConstants.SIGHTINGTYPE_NOT_FOUND, HttpStatus.NOT_FOUND));
		if (tipo.isActive()) {
			throw new ReservaException(SightingConstants.SIGHTINGTYPE_IS_ACTIVE, HttpStatus.BAD_REQUEST);
		}
		try {
			tipo.setActive(true);
			sightingTypeRepository.save(tipo);
			return new Responses<>(true, SightingConstants.SIGHTINGTYPE_IS_ACTIVE, getById(id));
		} catch (Exception e) {
			throw new ReservaException(SightingConstants.REQUEST_FAILURE, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public ResponsePageable<SightingTypeResponseDto> getAll(String name, String category, int page, int size, String orderBy, String sortBy) {
		try {
			if(page < 1) page = 1; if(size < 1) size = 999999;
			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(
					orderBy.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy.toLowerCase()));
			Page<SightingType> pageTipo;
			if (!name.isEmpty()) {
				pageTipo = sightingTypeRepository.findByNameContainingAndActive(name, true, pageable);
			} else if (!category.isEmpty()) {
				pageTipo = sightingTypeRepository.findByCategoryAndActive(category, true, pageable);
			} else {
				pageTipo = sightingTypeRepository.findByActive(true, pageable);
			}
			return new ResponsePageable<>(page, pageTipo.getTotalPages(),
					pageTipo.getContent().stream()
							.map(sightingType -> modelMapper.map(sightingType, SightingTypeResponseDto.class))
							.collect(Collectors.toList()));
		} catch (Exception e) {
			throw new ReservaException(SightingConstants.SIGHTINGTYPE_LIST_ERROR, HttpStatus.EXPECTATION_FAILED);
		}
	}

}

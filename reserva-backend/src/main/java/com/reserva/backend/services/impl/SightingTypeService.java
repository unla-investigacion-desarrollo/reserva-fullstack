package com.reserva.backend.services.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.reserva.backend.dto.SightingTypeRequestDto;
import com.reserva.backend.dto.SightingTypeResponseDto;
import com.reserva.backend.entities.SightingType;
import com.reserva.backend.repositorys.ISightingTypeRepository;
import com.reserva.backend.services.ISightingTypeService;
import com.reserva.backend.exceptions.ReservaException;

@Service
public class SightingTypeService implements ISightingTypeService{
	
	@Autowired
	private ISightingTypeRepository tipoAvistamientoRepository;
	
	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public SightingTypeResponseDto create(SightingTypeRequestDto request) {
		if(tipoAvistamientoRepository.existsByName(request.getName())) {
			throw new ReservaException("Ya existe un tipo_avistamiento con ese nombre", HttpStatus.BAD_REQUEST);
		}
		try {
			SightingType tipo = modelMapper.map(request, SightingType.class);
			tipo.setActive(true);
			tipoAvistamientoRepository.save(tipo);
			SightingTypeResponseDto response = modelMapper.map(tipo, SightingTypeResponseDto.class);
			return response;
		}catch(MappingException  e) {
			throw new ReservaException("algo salió mal en el mapeo", HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public SightingTypeResponseDto getById(long id) {
		Optional<SightingType> tipo = tipoAvistamientoRepository.findById(id);
		if(!tipo.isPresent()) {
			throw new ReservaException("no hay ningun tipo_avistamiento con id: "+id, HttpStatus.NOT_FOUND);
		}
		SightingTypeResponseDto response = modelMapper.map(tipo.get(), SightingTypeResponseDto.class);
		return response;
	}

	@Override
	public String update(long id) {
		Optional<SightingType> tipo = tipoAvistamientoRepository.findById(id);
		if(!tipo.isPresent()) {
			throw new ReservaException("no hay ningun tipo_avistamiento con id: "+id, HttpStatus.NOT_FOUND);
		}
		if(!tipo.get().isActive()) {
			throw new ReservaException("tipo_avistamiento no se encuentra activo", HttpStatus.BAD_REQUEST);
		}
		return null;
	}

	@Override
	public String delete(long id) {
		Optional<SightingType> tipo = tipoAvistamientoRepository.findById(id);
		if(!tipo.isPresent()) {
			throw new ReservaException("no hay ningun tipo_avistamiento con id: "+id, HttpStatus.NOT_FOUND);
		}
		if(!tipo.get().isActive()) {
			throw new ReservaException("tipo_avistamiento ya se encuentra dado de baja", HttpStatus.BAD_REQUEST);
		}
		tipo.get().setActive(false);
		tipoAvistamientoRepository.save(tipo.get());
		return "tipo_avistamiento dado de baja";
	}

	@Override
	public String restore(long id) {
		Optional<SightingType> tipo = tipoAvistamientoRepository.findById(id);
		if(!tipo.isPresent()) {
			throw new ReservaException("no hay ningun tipo_avistamiento con id: "+id, HttpStatus.NOT_FOUND);
		}
		if(tipo.get().isActive()) {
			throw new ReservaException("tipo_avistamiento ya se encuentra dado de alta", HttpStatus.BAD_REQUEST);
		}
		tipo.get().setActive(true);
		tipoAvistamientoRepository.save(tipo.get());
		return "tipo_avistamiento dado de alta";
	}

	@Override
	public List<SightingTypeResponseDto> getAll(String name, String category, int page, int size, String orderBy, String sortBy) {
		try {
			if(page < 1) page = 1; if(size < 1) size = 999999;
			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(orderBy.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy.toLowerCase()));
			Page<SightingType> pageTipo;
			if(StringUtils.isEmpty(name)) {
				pageTipo = tipoAvistamientoRepository.findByNameContaining(name, pageable);
			}else {
				pageTipo = tipoAvistamientoRepository.findByCategory(category, pageable);
			}
			List<SightingTypeResponseDto> response = new ArrayList<>();
			for(SightingType t : pageTipo.getContent()) {
				response.add(modelMapper.map(t, SightingTypeResponseDto.class));
			}
			return response;
			}catch(Exception e) {
			throw new ReservaException("No se pudieron listar los tipo_avistamiento", HttpStatus.EXPECTATION_FAILED);
		}
	}

}

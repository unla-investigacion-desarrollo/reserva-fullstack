package com.reserva.backend.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.reserva.backend.dto.FieldRequestDto;
import com.reserva.backend.entities.Field;
import com.reserva.backend.entities.Sighting;
import com.reserva.backend.exceptions.ReservaException;
import com.reserva.backend.repositorys.IFieldRepository;
import com.reserva.backend.repositorys.ISightingRepository;
import com.reserva.backend.services.IFieldService;
import com.reserva.backend.util.Responses;

@Service
public class FieldService implements IFieldService {

    @Autowired
    private IFieldRepository fieldRepository;

    @Autowired
    private ISightingRepository sightingRepository;

    private ModelMapper modelmapper = new ModelMapper();

    @Override
    public Responses<FieldRequestDto> create(long sightingId, FieldRequestDto request) {
        Sighting sighting = sightingRepository.findById(sightingId)
                .orElseThrow(() -> new ReservaException("No se puede asignar un campo a un avistamiento no existente",
                        HttpStatus.BAD_REQUEST));
        try {
            Field field = new Field();
            field.setTitle(request.getTitle());
            field.setDescription(request.getDescription());
            field.setActive(true);
            field.setSighting(sighting);
            fieldRepository.save(field);
            FieldRequestDto response = modelmapper.map(field, FieldRequestDto.class);
            return new Responses<>(true, "field creado", response);
        } catch (Exception e) {
            throw new ReservaException("request failure", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public FieldRequestDto getById(long id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new ReservaException("not found field", HttpStatus.NOT_FOUND));
        FieldRequestDto response = modelmapper.map(field, FieldRequestDto.class);
        return response;
    }

    @Override
    public Responses<FieldRequestDto> update(long id, FieldRequestDto request) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new ReservaException("not found field", HttpStatus.NOT_FOUND));
        if (!field.isActive()) {
            throw new ReservaException("not found field", HttpStatus.NOT_FOUND);
        }
        try {
            field.setTitle(request.getTitle());
            field.setDescription(request.getDescription());
            fieldRepository.save(field);
            return new Responses<>(true, "update ok", getById(id));
        } catch (Exception e) {
            throw new ReservaException("request failure", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Responses<FieldRequestDto> delete(long id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new ReservaException("not found field", HttpStatus.NOT_FOUND));
        if (!field.isActive()) {
            throw new ReservaException("not found field", HttpStatus.NOT_FOUND);
        }
        try {
            field.setActive(false);
            fieldRepository.save(field);
            return new Responses<>(true, "delete ok", getById(id));
        } catch (Exception e) {
            throw new ReservaException("request failure", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Responses<FieldRequestDto> restore(long id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new ReservaException("not found field", HttpStatus.NOT_FOUND));
        if (field.isActive()) {
            throw new ReservaException("found field", HttpStatus.NOT_FOUND);
        }
        try {
            field.setActive(true);
            fieldRepository.save(field);
            return new Responses<>(true, "restore ok", getById(id));
        } catch (Exception e) {
            throw new ReservaException("request failure", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public List<FieldRequestDto> getBySightingId(long sightingId) {
        Sighting sighting = sightingRepository.findById(sightingId)
                .orElseThrow(() -> new ReservaException("sighting not found", HttpStatus.NOT_FOUND));
        return sighting.getFields().stream().map(field -> modelmapper.map(field, FieldRequestDto.class))
                .collect(Collectors.toList());
    }

}

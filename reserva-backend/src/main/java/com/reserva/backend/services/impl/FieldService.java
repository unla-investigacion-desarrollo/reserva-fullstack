package com.reserva.backend.services.impl;

import java.util.List;

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
        try{                
        Field field = new Field();
        field.setTitle(request.getTitle());
        field.setDescription(request.getDescription());
        field.setActive(true);
        field.setSighting(sighting);
        fieldRepository.save(field);
        FieldRequestDto response = modelmapper.map(field, FieldRequestDto.class);
        return new Responses<>(true, "field creado", response);
        }catch(Exception e){
            throw new ReservaException("request failure", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public FieldRequestDto getById(long id) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> new ReservaException("not found field", HttpStatus.NOT_FOUND));
        FieldRequestDto response = modelmapper.map(field, FieldRequestDto.class);
        return response;
    }

    @Override
    public Responses<FieldRequestDto> update(long id, FieldRequestDto request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Responses<FieldRequestDto> delete(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Responses<FieldRequestDto> restore(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restore'");
    }

    @Override
    public List<FieldRequestDto> getBySightingId(long sightingId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBySightingId'");
    }

}

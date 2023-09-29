package com.reserva.backend.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.reserva.backend.dto.SightingRequestDto;
import com.reserva.backend.dto.SightingResponseDto;
import com.reserva.backend.entities.Field;
import com.reserva.backend.entities.Sighting;
import com.reserva.backend.entities.SightingType;
import com.reserva.backend.entities.User;
import com.reserva.backend.exceptions.ReservaException;
import com.reserva.backend.repositorys.ISightingRepository;
import com.reserva.backend.repositorys.ISightingTypeRepository;
import com.reserva.backend.repositorys.IUserRepository;
import com.reserva.backend.services.ISightingService;

@Service
public class SightingService implements ISightingService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ISightingRepository sightingRepository;

    @Autowired
    private ISightingTypeRepository sightingTypeRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public boolean create(SightingRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ReservaException("el usuario no fue encontrado", HttpStatus.NOT_FOUND));
        Sighting sighting = new Sighting();
        sighting.setName(request.getName());
        sighting.setScientificName(request.getScientificName());
        sighting.setLatitude(request.getLatitude());
        sighting.setLongitude(request.getLongitude());
        SightingType tipo = sightingTypeRepository.findByName(request.getType());
        if (tipo == null) {
            throw new ReservaException("El Tipo_Avistamiento no es valido", HttpStatus.BAD_REQUEST);
        }
        sighting.setType(tipo);
        if (user.getRole().getName().equals("ROLE_PERSONAL_RESERVA")) {
            sighting.setStatus("APROBADO");
            sighting.setApprovedBy(user);
        } else {
            sighting.setStatus("PENDIENTE");
        }
        sighting.setCreatedAt(new Date());
        sighting.setCreatedBy(user);
        List<Field> fields = new ArrayList<>();
        for (int i = 0; i < request.getFields().size(); i++) {
            Field field = new Field();
            field.setTitle(request.getFields().get(i).getTitle());
            field.setDescription(request.getFields().get(i).getTitle());
            fields.add(field);
        }
        sighting.setFields(fields);
        try {
            sightingRepository.save(sighting);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public SightingResponseDto getById(long id) {
        Optional<Sighting> sighting = sightingRepository.findById(id);
        if(!sighting.isPresent()){
            throw new ReservaException("no hay ningun avistamiento con id: "+id, HttpStatus.NOT_FOUND);
        }
        SightingResponseDto response = modelMapper.map(sighting.get(), SightingResponseDto.class);
        return response;
    }
    


    
}

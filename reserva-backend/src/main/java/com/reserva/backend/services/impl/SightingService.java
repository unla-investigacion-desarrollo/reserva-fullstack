package com.reserva.backend.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.reserva.backend.dto.SightingRequestDto;
import com.reserva.backend.dto.SightingResponseDto;
import com.reserva.backend.dto.UpdateStatusDto;
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
        sighting.setActive(true);
        SightingType tipo = sightingTypeRepository.findByName(request.getType());
        if (tipo == null || !tipo.isActive()) {
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
        if (!sighting.isPresent() || !sighting.get().isActive()) {
            throw new ReservaException("no hay ningun avistamiento con id: " + id, HttpStatus.NOT_FOUND);
        }
        SightingResponseDto response = modelMapper.map(sighting.get(), SightingResponseDto.class);
        return response;
    }

    @Override
    public List<SightingResponseDto> getByUserId(long id) {
        List<Sighting> sightings = sightingRepository.findByUserId(id);
        return sightings.stream().map(sighting -> modelMapper.map(sighting, SightingResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SightingResponseDto> getAll(String status, String type, int page, int size, String orderBy,
            String sortBy) {
        try{
            if(page < 1 ) page = 1; if(size < 1) size = 999999;
            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(
                orderBy.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy.toLowerCase()));
                Page<Sighting> pageTipo;
                if(!status.isEmpty()){
                    pageTipo = sightingRepository.findByStatus(status, pageable);
                }else if(!type.isEmpty()){
                    pageTipo = sightingRepository.findByType(type, pageable);
                }else{
                    pageTipo = sightingRepository.findByActive(true, pageable);
                }
                List<SightingResponseDto> response = new ArrayList<>();
                for(Sighting t : pageTipo.getContent()){
                    response.add(modelMapper.map(t, SightingResponseDto.class));
                }
                return response;
        }catch(Exception e){
            throw new ReservaException("No se pudieron listar los avistamientos", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public String updateStatus(UpdateStatusDto request) {
        User user = userRepository.findById(request.getApprovedById()).orElseThrow(() -> new ReservaException("No existe un usuario con ese id", HttpStatus.NOT_FOUND));
        Sighting sighting = sightingRepository.findById(request.getIdSighting()).orElseThrow(() -> new ReservaException("No existe un avistamiento con ese id", HttpStatus.NOT_FOUND));
        sighting.setStatus(request.getStatus());
        sighting.setApprovedBy(user);
        sightingRepository.save(sighting);
        return "Avistamiento con id "+sighting.getId()+" fue "+sighting.getStatus();
    }

}

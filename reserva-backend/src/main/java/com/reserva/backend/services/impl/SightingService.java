package com.reserva.backend.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reserva.backend.constants.SightingConstants;
import com.reserva.backend.dto.FieldRequestDto;
import com.reserva.backend.dto.SightingCustomResponseDto;
import com.reserva.backend.dto.SightingRequestDto;
import com.reserva.backend.dto.SightingResponseDto;
import com.reserva.backend.dto.UpdateStatusDto;
import com.reserva.backend.entities.Field;
import com.reserva.backend.entities.Image;
import com.reserva.backend.entities.Sighting;
import com.reserva.backend.entities.SightingType;
import com.reserva.backend.entities.User;
import com.reserva.backend.exceptions.ReservaException;
import com.reserva.backend.repositorys.ISightingRepository;
import com.reserva.backend.repositorys.ISightingTypeRepository;
import com.reserva.backend.repositorys.IUserRepository;
import com.reserva.backend.services.ISightingService;
import com.reserva.backend.services.IStorageService;

@Service
public class SightingService implements ISightingService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ISightingRepository sightingRepository;

    @Autowired
    private ISightingTypeRepository sightingTypeRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private IStorageService storageService;

    @Override
    public SightingResponseDto create(SightingRequestDto request, List<MultipartFile> files) {
        try {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new ReservaException(SightingConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
            Sighting sighting = new Sighting();
            sighting.setName(request.getName());
            sighting.setScientificName(request.getScientificName());
            sighting.setLatitude(request.getLatitude());
            sighting.setLongitude(request.getLongitude());
            sighting.setActive(true);
            SightingType tipo = sightingTypeRepository.findByName(request.getType());
            if (tipo == null || !tipo.isActive()) {
                throw new ReservaException(SightingConstants.SIGHTINGTYPE_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }
            sighting.setType(tipo);
            if (user.getRole().getName().equals(SightingConstants.ADMIN)) {
                sighting.setStatus(SightingConstants.APPROVED_STATUS);
                sighting.setApprovedBy(user);
            } else {
                sighting.setStatus(SightingConstants.PENDING_STATUS);
            }
            sighting.setCreatedAt(new Date());
            sighting.setCreatedBy(user);
            List<Field> fields = new ArrayList<>();
            for (FieldRequestDto f : request.getFields()) {
                Field field = new Field();
                field.setTitle(f.getTitle());
                field.setDescription(f.getDescription());
                fields.add(field);
            }
            sighting.setFields(fields);
            List<Image> images = new ArrayList<>();
            for (MultipartFile m : files) {
                String url = storageService.saveImage(m);
                Image image = new Image();
                image.setUrl(url);
                image.setSighting(sighting);
                images.add(image);
            }
            sighting.setImages(images);
            sightingRepository.save(sighting);
            SightingResponseDto response = modelMapper.map(sighting, SightingResponseDto.class);
            return response;
        } catch (MappingException e) {
            throw new ReservaException(SightingConstants.MAPPING_WRONG, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public SightingResponseDto getById(long id) {
        Optional<Sighting> sighting = sightingRepository.findById(id);
        if (!sighting.isPresent() || !sighting.get().isActive()) {
            throw new ReservaException(SightingConstants.SIGHTING_NOT_FOUND, HttpStatus.NOT_FOUND);
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
    public SightingCustomResponseDto getAll(String status, String type, int page, int size, String orderBy,
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
                // Primero hago el mapeo de los avistamientos y despues mando la respuesta personalizada
                List<SightingResponseDto> sightings = new ArrayList<>();
                for(Sighting t : pageTipo.getContent()){
                    SightingResponseDto sighting = modelMapper.map(t, SightingResponseDto.class);
                    sightings.add(sighting);
                }
                SightingCustomResponseDto response = new SightingCustomResponseDto();
                response.setCurrentPage(page);
                response.setAmountOfPages(pageTipo.getTotalPages());
                response.setSightings(sightings);
                return response;
        }catch(Exception e){
            throw new ReservaException(SightingConstants.SIGHTING_LIST_ERROR, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public String updateStatus(UpdateStatusDto request) {
        User user = userRepository.findById(request.getApprovedById()).orElseThrow(() -> new ReservaException(SightingConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        Sighting sighting = sightingRepository.findById(request.getIdSighting()).orElseThrow(() -> new ReservaException(SightingConstants.SIGHTING_NOT_FOUND, HttpStatus.NOT_FOUND));
        sighting.setStatus(request.getStatus());
        sighting.setApprovedBy(user);
        sightingRepository.save(sighting);
        return String.format(SightingConstants.SIGHTING_STATUS, sighting.getId(), sighting.getStatus());
    }

}

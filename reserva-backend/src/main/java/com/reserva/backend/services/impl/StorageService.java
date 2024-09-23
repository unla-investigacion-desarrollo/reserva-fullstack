package com.reserva.backend.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reserva.backend.config.StorageProperties;
import com.reserva.backend.constants.SightingConstants;
import com.reserva.backend.constants.StorageConstants;
import com.reserva.backend.entities.Image;
import com.reserva.backend.exceptions.ReservaException;
import com.reserva.backend.repositorys.IStorageRepository;
import com.reserva.backend.services.IStorageService;
import com.reserva.backend.util.Response;
import com.reserva.backend.util.Responses;

@Service
public class StorageService implements IStorageService {

    private final Path storageLocation;

    @Autowired
    private IStorageRepository storageRepository;

    /*
     * Para asegurarnos que vamos a guardar correctamente las imagenes en una ubicacion valida
     */
    @Autowired
    public StorageService(StorageProperties storageProperties) {
        if (storageProperties.getStorageLocation().trim().length() == 0) {
            throw new ReservaException(SightingConstants.LOCATION_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        storageLocation = Paths.get(storageProperties.getStorageLocation());
    }

    @Override
    public String saveImage(MultipartFile image) {
        try {
            if (image.isEmpty()) {
                throw new ReservaException(SightingConstants.IMAGE_UPLOAD_REQUIRED,
                        HttpStatus.BAD_REQUEST);
            }
            if (image.getContentType() == null || !image.getContentType().startsWith("image/")) {
                throw new ReservaException(SightingConstants.INVALID_FORMAT, HttpStatus.BAD_REQUEST);
            }
            // Esto se puede cambiar en caso de se quiera guardar la ruta absoluta con
            // storageLocation.resolve(Paths.get(uniqueFileName)).normalize().toAbsolutePath();
            // filename.substring(filename.lastIndexOf("."));
            String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
            String nameImage = UUID.randomUUID().toString() + extension;
            Path path = getPath(nameImage);
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            }
            return nameImage;
        } catch (IOException e) {
            throw new ReservaException(SightingConstants.IMAGE_UPLOAD_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Path getPath(String url) {
        return storageLocation.resolve(url);
    }

    @Override
    public Resource getImage(String url) {
        try {
            Path path = getPath(url);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ReservaException(StorageConstants.IMAGE_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            throw new ReservaException(StorageConstants.IMAGE_URL_INVALID, HttpStatus.BAD_REQUEST);
        }   
    }
    
    @Override
    public Resource getImage(long id) {
        Image image = storageRepository.findById(id).orElseThrow(() -> new ReservaException(StorageConstants.IMAGE_NOT_FOUND, HttpStatus.NOT_FOUND));
        try {
            Path path = getPath(image.getUrl());
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ReservaException(StorageConstants.IMAGE_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            throw new ReservaException(StorageConstants.IMAGE_URL_INVALID, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Responses<String> delete(long id) {
        Image image = storageRepository.findById(id).orElseThrow(() -> new ReservaException(StorageConstants.IMAGE_NOT_FOUND, HttpStatus.NOT_FOUND));
        try {
            Path path = getPath(image.getUrl());
            Files.deleteIfExists(path);
            storageRepository.delete(image);
            return Response.success(StorageConstants.IMAGE_DELETE_SUCCESS, null);
        } catch (IOException e) {
            throw new ReservaException(StorageConstants.IMAGE_URL_INVALID, HttpStatus.BAD_REQUEST);
        }
    }

}

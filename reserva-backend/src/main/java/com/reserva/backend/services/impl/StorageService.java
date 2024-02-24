package com.reserva.backend.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reserva.backend.config.StorageProperties;
import com.reserva.backend.exceptions.ReservaException;
import com.reserva.backend.services.IStorageService;

@Service
public class StorageService implements IStorageService {

    private final Path storageLocation;

    /*
     * Para asegurarnos que vamos a guardar correctamente las imagenes en una ubicacion valida
     */
    @Autowired
    public StorageService(StorageProperties storageProperties) {
        if (storageProperties.getStorageLocation().trim().length() == 0) {
            throw new ReservaException("Ubicacion no encontrada", HttpStatus.NOT_FOUND);
        }
        storageLocation = Paths.get(storageProperties.getStorageLocation());
    }

    @Override
    public String saveImage(MultipartFile image, String name) {
        try {
            if (image.isEmpty()) {
                throw new ReservaException("Es necesario subir al menos una imagen por cada avistamiento",
                        HttpStatus.BAD_REQUEST);
            }
            if (image.getContentType() == null || !image.getContentType().startsWith("image/")) {
                throw new ReservaException("El formato no es valido", HttpStatus.BAD_REQUEST);
            }
            // Esto se puede cambiar en caso de se quiera guardar la ruta absoluta con
            // storageLocation.resolve(Paths.get(uniqueFileName)).normalize().toAbsolutePath();
            String nameImage = UUID.randomUUID().toString() + "_" + image.getOriginalFilename() + "_" + name;
            Path path = getPath(nameImage);
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            }
            return nameImage;
        } catch (IOException e) {
            throw new ReservaException("Error al guardar la imagen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Path getPath(String url) {
        return storageLocation.resolve(url);
    }

}

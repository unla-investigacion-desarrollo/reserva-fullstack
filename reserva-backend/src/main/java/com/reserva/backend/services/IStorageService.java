package com.reserva.backend.services;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

    public String saveImage(MultipartFile image, String name);
    public Path getPath(String url);
    public Resource getImage(String url);
    
}

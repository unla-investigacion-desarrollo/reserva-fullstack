package com.reserva.backend.services;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    
    public String saveImage(MultipartFile image);
}

package com.reserva.backend.services;

import java.nio.file.Path;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.reserva.backend.util.Responses;

public interface IStorageService {

    public String saveImage(MultipartFile image);
    public Path getPath(String url);
    public Resource getImage(String url);
    public Resource getImage(long id);
    public Responses<String> delete(long id);

    List<String> getImageUrlsBySightingId(Long sightingId);
    
}

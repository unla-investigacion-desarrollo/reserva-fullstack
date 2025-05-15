package com.reserva.backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.reserva.backend.config.StorageProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ReservaBackendApplication {

	@Autowired
	private StorageProperties storageProperties;

	public static void main(String[] args) {
		SpringApplication.run(ReservaBackendApplication.class, args);
	}

	@PostConstruct
	private void initializeStorage() {
		try {
			Path storageLocation = Paths.get(storageProperties.getStorageLocation());
			if (!Files.exists(storageLocation)) {
				Files.createDirectories(storageLocation);
			}
		} catch (IOException e) {
			log.error("Error al crear el directorio de imagenes: {}", e.getMessage());
		}
	}

}

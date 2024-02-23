package com.reserva.backend;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;

import com.reserva.backend.config.StorageProperties;
import com.reserva.backend.exceptions.ReservaException;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ReservaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservaBackendApplication.class, args);
	}

	@Autowired
	private StorageProperties storageProperties;

	@PostConstruct
	private void initializeStorage() {
		try {
			Path storageLocation = Paths.get(storageProperties.getStorageLocation());
			if (!Files.exists(storageLocation)) {
				Files.createDirectories(storageLocation);
			}
		} catch (Exception e) {
			throw new ReservaException("Error al iniciar el storage de imagenes " + e, HttpStatus.BAD_REQUEST);
		}
	}

}

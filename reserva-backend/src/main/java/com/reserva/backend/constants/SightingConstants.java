package com.reserva.backend.constants;

public class SightingConstants {

    // Admin
    public static final String ADMIN = "ROLE_PERSONAL_RESERVA";

    // Status
    public static final String APPROVED_STATUS = "APROBADO";
    public static final String PENDING_STATUS = "PENDIENTE";
    public static final String REJECTED_STATUS = "RECHAZADO";

    // Constantes generales
    public static final String SIGHTINGTYPE_ALREADY_EXIST = "Ya existe un tipo de avistamiento con ese nombre";
    public static final String SIGHTINGTYPE_NOT_FOUND = "No se encontró ningun tipo de avistamiento";
    public static final String SIGHTINGTYPE_IS_ACTIVE = "El tipo de avistamiento se encuentra activo";
    public static final String SIGHTINGTYPE_LIST_ERROR = "Error al listar los tipos de avistamientos";
    public static final String SIGHTINGTYPE_UPDATE_SUCCESSFUL = "Tipo de avistamiento actualizado correctamente";
    public static final String SIGHTINGTYPE_DELETE_SUCCESSFUL = "Tipo de avistamiento borrado correctamente";

    public static final String USER_NOT_FOUND = "El usuario no fue encontrado";
    public static final String SIGHTING_LIST_ERROR = "Error al listar los avistamientos";
    public static final String SIGHTING_NOT_FOUND = "Avistamiento no encontrado";
    public static final String SIGHTING_STATUS = "Avistamiento: %s Status: %s";
    
    // Mapeo 
    public static final String REQUEST_FAILURE = "Algo salió mal durante la solicitud";

    // Storage
    public static final String LOCATION_NOT_FOUND = "Ubicacion no encontrada";
    public static final String INVALID_FORMAT = "Formato no valido";
    public static final String IMAGE_UPLOAD_ERROR = "Error al guardar la imagen";
    public static final String IMAGE_UPLOAD_REQUIRED = "Es necesario subir al menos una imagen por cada avistamiento";


}

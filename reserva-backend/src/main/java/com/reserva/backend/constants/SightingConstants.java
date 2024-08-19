package com.reserva.backend.constants;

public class SightingConstants {

    // Status
    public static final String STATUS_APPROVED = "APROBADO";
    public static final String STATUS_PENDING = "PENDIENTE";
    public static final String STATUS_REJECTED = "RECHAZADO";

    // Constantes generales
    public static final String SIGHTINGTYPE_OK = "Tipo avistamiento OK.";
    public static final String SIGHTINGTYPE_CREATE_SUCCESS = "Tipo de avistamiento creado correctamente.";
    public static final String SIGHTINGTYPE_TAKEN = "Ya existe un tipo de avistamiento con ese nombre.";
    public static final String SIGHTINGTYPE_NOT_FOUND = "No se encontró ningun tipo de avistamiento.";
    public static final String SIGHTINGTYPE_IS_ACTIVE = "El tipo de avistamiento está activo.";
    public static final String SIGHTINGTYPE_LIST_ERROR = "Error al listar los tipos de avistamientos.";
    public static final String SIGHTINGTYPE_UPDATE_SUCCESS = "Tipo de avistamiento actualizado correctamente.";
    public static final String SIGHTINGTYPE_DELETE_SUCCESS = "Tipo de avistamiento eliminado correctamente.";
    public static final String SIGHTINGTYPE_RESTORE_SUCCESS = "Tipo de avistamiento restaurado correctamente.";
    public static final String SIGHTINGTYPE_RETRIEVE_ERROR = "Error al recuperar los tipos de avistamientos.";

    public static final String SIGHTING_OK = "Avistamiento OK.";
    public static final String SIGHTING_LIST_ERROR = "Error al listar los avistamientos.";
    public static final String SIGHTING_NOT_FOUND = "Avistamiento no encontrado.";
    public static final String SIGHTING_STATUS = "Avistamiento: %s, Estado: %s.";
    public static final String SIGHTING_CREATE_SUCCESS = "Avistamiento creado correctamente.";
    public static final String SIGHTING_UPDATE_SUCCESS = "Avistamiento actualizado correctamente.";
    public static final String SIGHTING_DELETE_SUCCESS = "Avistamiento borrado correctamente.";
    public static final String SIGHTING_RESTORE_SUCCESS = "Avistamiento restaurado correctamente.";
    public static final String SIGHTING_IS_ACTIVE = "Avistamiento está activo.";
    public static final String SIGHTING_NOT_OWNED = "No se puede actualizar un avistamiento que no te pertenece.";
    public static final String SIGHTING_RETRIEVE_ERROR = "Error al recuperar los avistamientos.";
;    
    // Mapeo 
    public static final String REQUEST_ERROR = "Ocurrió un error durante la solicitud.";

    // Fields
    public static final String FIELD_OK = "Campo OK.";
    public static final String FIELD_IS_ACTIVE = "Campo está existe";
    public static final String FIELD_ASSIGNMENT_ERROR = "No se puede asignar un campo a un avistamiento inexistente.";
    public static final String FIELD_RETRIEVE_ERROR = "Error al recuperar los campos.";
    public static final String FIELD_CREATE_SUCCESS = "Campo creado correctamente.";
    public static final String FIELD_NOT_FOUND = "Campo no encontrado.";
    public static final String FIELD_UPDATE_SUCCESS = "Campo actualizado correctamente.";
    public static final String FIELD_DELETE_SUCCESS = "Campo eliminado correctamente.";
    public static final String FIELD_RESTORE_SUCCESS = "Campo restaurado correctamente.";
    public static final String FIELD_REQUEST_FAILURE = "Error durante la solicitud.";

    // Storage
    public static final String LOCATION_NOT_FOUND = "Ubicación no encontrada.";
    public static final String INVALID_FORMAT = "Formato no válido.";
    public static final String IMAGE_UPLOAD_FAILED = "Error al subir la imagen.";
    public static final String IMAGE_UPLOAD_REQUIRED = "Es necesario subir al menos una imagen por cada avistamiento.";


}

package com.reserva.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusDto {

    private long idSighting;
    private long approvedById;
    private String status;
    
}

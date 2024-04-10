package com.reserva.backend.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePageable<T> {

    private int currentPage;
    private int amountOfPages;
    private List<T> data;
    
}

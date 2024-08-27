package com.reserva.backend.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Response {

    public static <T> Responses<T> success(String result, T data) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return new Responses<>(new Date(), result, request.getRequestURI(), true, data);
    }
    
}

package com.reserva.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import com.reserva.backend.entities.User;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private long id;
    private String name;
    private String lastname; // Añadir
    private String username;
    private String email;
    private String avatarUrl; // Añadir
    private String location; // Añadir
    private LocalDateTime createdAt; // Añadir
    private boolean active;
    private RoleDto role;
    
    // Añade constructor desde la entidad User
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.active = user.isActive();
    }
}

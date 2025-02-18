package com.jve.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDTO {

    private Long idUser;

    @NotBlank(message = "El rol es obligatorio")
    private String role;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 45, message = "El nombre de usuario debe tener entre 4 y 45 caracteres")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotNull(message = "La especialidad es obligatoria")
    private Long especialidadId;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 45, message = "El nombre debe tener entre 2 y 45 caracteres")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, max = 45, message = "Los apellidos deben tener entre 2 y 45 caracteres")
    private String apellidos;

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 9, max = 9, message = "El DNI debe tener 9 caracteres")
    private String dni;

    // Getters y Setters
    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(Long especialidadId) {
        this.especialidadId = especialidadId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}

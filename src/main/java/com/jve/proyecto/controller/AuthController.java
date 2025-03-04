package com.jve.proyecto.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jve.proyecto.dto.AuthRequest;
import com.jve.proyecto.dto.LoginResponse;
import com.jve.proyecto.dto.UserDTO;
import com.jve.proyecto.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar un nuevo usuario", description = "Permite registrar un nuevo usuario en el sistema con los datos proporcionados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario registrado con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UserDTO> register(
            @Parameter(description = "Datos del nuevo usuario", required = true) 
            @RequestBody @Valid UserDTO userDTO) {
        UserDTO savedUser = authService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario autenticarse con su nombre de usuario y contraseña.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "400", description = "Credenciales inválidas"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<LoginResponse> login(
            @Parameter(description = "Credenciales de inicio de sesión", required = true) 
            @RequestBody AuthRequest loginDTO) {
        LoginResponse response = authService.login(loginDTO);
        return ResponseEntity.ok(response);
    }
}

package com.jve.proyecto.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jve.proyecto.dto.UserDTO;
import com.jve.proyecto.service.UserService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Traer todos los usuarios", description = "Trae la lista de todos los usuarios registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<UserDTO>> TraerTodosLosUsuarios() {
        List<UserDTO> users = userService.TraerTodos();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Traer usuario por ID", description = "Trae los detalles de un usuario mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UserDTO> TraerUsuarioPorId(
            @Parameter(description = "ID del usuario a buscar", required = true)
            @PathVariable Long id) {
        UserDTO user = userService.TraerUsuarioPorId(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Traer usuarios por rol", description = "Trae una lista de usuarios según su rol (ej. ADMIN, USER)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios con el rol solicitado obtenida con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<UserDTO>> getUsersByRole(
            @Parameter(description = "Rol de los usuarios (ej. ADMIN, USER)", required = true)
            @PathVariable String role) {
        List<UserDTO> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Permite actualizar los datos de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UserDTO> actualizarUsuario(
            @Parameter(description = "ID del usuario a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados del usuario", required = true)
            @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.actualizarUsuario(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }
}

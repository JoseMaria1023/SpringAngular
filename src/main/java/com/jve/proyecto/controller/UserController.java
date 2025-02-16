package com.jve.proyecto.controller;

import com.jve.proyecto.dto.UserDTO;
import com.jve.proyecto.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> crearUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO savedUser = userService.guardarUsuario(userDTO);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> obtenerTodosLosUsuarios() {
        List<UserDTO> users = userService.obtenerTodos();
        return ResponseEntity.ok(users);
    }
}

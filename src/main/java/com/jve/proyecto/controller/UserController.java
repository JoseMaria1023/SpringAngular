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


    @GetMapping
    public ResponseEntity<List<UserDTO>> TraerTodosLosUsuarios() {
        List<UserDTO> users = userService.TraerTodos();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@PathVariable String role) {
        List<UserDTO> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
    UserDTO updatedUser = userService.actualizarUsuario(id, userDTO);
    return ResponseEntity.ok(updatedUser);
}

}

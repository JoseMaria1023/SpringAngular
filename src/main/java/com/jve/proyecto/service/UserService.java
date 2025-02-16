package com.jve.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jve.proyecto.converter.UserConverter;
import com.jve.proyecto.dto.UserDTO;
import com.jve.proyecto.entity.User;
import com.jve.proyecto.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final BCryptPasswordEncoder passwordEncoder; // Inyectamos el codificador de contraseñas

    // Inyectamos el BCryptPasswordEncoder
    public UserService(UserRepository userRepository, UserConverter userConverter, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder; // Guardamos la instancia
    }

    public UserDTO guardarUsuario(UserDTO userDTO) {
        // Convertir el DTO a la entidad User
        User user = userConverter.dtoToEntity(userDTO);

        // Codificar la contraseña antes de guardarla
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword); // Establecer la contraseña codificada

        // Establecer un rol por defecto si no se proporciona
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_EXPERTO");
        }

        // Guardar el usuario en la base de datos
        User savedUser = userRepository.save(user);

        // Devolver el DTO del usuario guardado
        return userConverter.entityToDto(savedUser);
    }

    public List<UserDTO> obtenerTodos() {
        return userRepository.findAll().stream()
                .map(userConverter::entityToDto)
                .collect(Collectors.toList());
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public String getEspecialidadByUsername(String username) {
        // Buscar el usuario por su nombre de usuario
        Optional<User> userOpt = userRepository.findByUsername(username);
    
        // Si el usuario existe, devolver su especialidad
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getEspecialidad() != null ? 
                user.getEspecialidad().toString() : null;  // Devolver la especialidad
        } else {
            throw new RuntimeException("Usuario no encontrado"); // O manejar de otra forma
        }
    }
    
}

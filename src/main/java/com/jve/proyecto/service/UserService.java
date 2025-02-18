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
    private final BCryptPasswordEncoder passwordEncoder; 

    public UserService(UserRepository userRepository, UserConverter userConverter, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder; 
    }

    public UserDTO guardarUsuario(UserDTO userDTO) {
        User user = userConverter.dtoToEntity(userDTO);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword); 

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_EXPERTO");
        }

        User savedUser = userRepository.save(user);

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

    public Long getEspecialidadByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
    
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getEspecialidad() != null ? 
                user.getEspecialidad().getIdEspecialidad() : null; 
        } else {
            throw new RuntimeException("Usuario no encontrado"); 
        }
    }
}

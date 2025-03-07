package com.jve.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jve.proyecto.converter.UserConverter;
import com.jve.proyecto.dto.UserDTO;
import com.jve.proyecto.entity.Especialidad;
import com.jve.proyecto.entity.User;
import com.jve.proyecto.exception.ErrorUsuarioEncontradoException;
import com.jve.proyecto.exception.ErrorUsuarioNotFoundException;
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

    public List<UserDTO> TraerTodos() {
        return userRepository.findAll().stream()
                .map(userConverter::entityToDto)
                .collect(Collectors.toList());
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Long getEspecialidadByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ErrorUsuarioNotFoundException("Usuario no encontrado"));
    
        return (user.getEspecialidad() != null) ? user.getEspecialidad().getIdEspecialidad() : null;
    }
    
     public List<UserDTO> getUsersByRole(String role) {
        List<User> users = userRepository.findByRole(role);
        return users.stream()
                .map(userConverter::entityToDto)
                .collect(Collectors.toList());
    }
    public UserDTO TraerUsuarioPorId(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ErrorUsuarioNotFoundException("Usuario no encontrado"));
        return userConverter.entityToDto(user);
    }
    
    
    public UserDTO actualizarUsuario(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ErrorUsuarioEncontradoException("Usuario no encontrado con ID: " + id));
    
        user.setRole(userDTO.getRole());
        user.setNombre(userDTO.getNombre());
        user.setApellidos(userDTO.getApellidos());
        user.setDni(userDTO.getDni());
    
        if (userDTO.getEspecialidadId() != null) {
            Especialidad especialidad = new Especialidad();
            especialidad.setIdEspecialidad(userDTO.getEspecialidadId());
            user.setEspecialidad(especialidad);
        }
    
        User updatedUser = userRepository.save(user);
        return userConverter.entityToDto(updatedUser);
    }
    
    

}

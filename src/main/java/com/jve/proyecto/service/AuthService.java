package com.jve.proyecto.service;

import com.jve.proyecto.dto.AuthRequest;
import com.jve.proyecto.dto.LoginResponse;
import com.jve.proyecto.dto.UserDTO;
import com.jve.proyecto.entity.User;
import com.jve.proyecto.exception.ErrorContrasenaException;
import com.jve.proyecto.repository.UserRepository;
import com.jve.proyecto.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, 
                       UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO register(UserDTO userDTO) {
        if (userDTO.getPassword().length() < 8) {
            throw new ErrorContrasenaException("La contraseña es muy pequena");
        }
        
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userService.guardarUsuario(userDTO);
    }

    public LoginResponse login(AuthRequest loginDTO) {
        Authentication authDTO = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authDTO);
        String token = jwtTokenProvider.generateToken(authentication);

        User user = (User) authentication.getPrincipal();
        Long especialidadId = (user.getEspecialidad() != null) ? user.getEspecialidad().getIdEspecialidad() : null;
        String especialidadNombre = (user.getEspecialidad() != null) ? user.getEspecialidad().getNombre() : null;
        Long idUser = user.getIdUser();

        return new LoginResponse(
                user.getUsername(),
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
                token,
                especialidadId,
                idUser,
                especialidadNombre
        );
    }
}
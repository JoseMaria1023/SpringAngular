package com.jve.proyecto.controller;

import com.jve.proyecto.dto.AuthRequest;
import com.jve.proyecto.dto.LoginResponse;
import com.jve.proyecto.dto.UserDTO;
import com.jve.proyecto.entity.User;
import com.jve.proyecto.exception.ErrorContrasenaException;
import com.jve.proyecto.security.JwtTokenProvider;
import com.jve.proyecto.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO userDTO) {
        if (userDTO.getPassword().length() < 8) {
            throw new ErrorContrasenaException("la contrasena es muy pequena");
        }
        UserDTO savedUser = userService.guardarUsuario(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // Endpoint de Login
    /*@PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(token);
    }*/

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login2(@RequestBody AuthRequest loginDTO) {
    
        Authentication authDTO = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword()
        );
        Authentication authentication = this.authenticationManager.authenticate(authDTO);
        String token = this.jwtTokenProvider.generateToken(authentication);
    
        User user = (User) authentication.getPrincipal();
    
        Long especialidadId = (user.getEspecialidad() != null) 
                ? user.getEspecialidad().getIdEspecialidad() 
                : null;
    
         Long idUser = user.getIdUser();

        return ResponseEntity.ok().body(
            new LoginResponse(
                    user.getUsername(),
                    authentication.getAuthorities().stream()
                                  .map(GrantedAuthority::getAuthority)
                                  .toList(),
                    token,
                    especialidadId,
                    idUser 
            )
    );
    }
}
package com.jve.proyecto.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // Habilitar CORS
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para API REST
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Estado sin sesión
                .authorizeRequests(requests -> requests
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll() // Permitir acceso público
                        .requestMatchers(HttpMethod.GET, "/api/participantes/todos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/participantes/todos").permitAll() // Permitir acceso público
                        .requestMatchers(HttpMethod.POST, "/api/participantes/crear").hasAuthority("ROLE_EXPERTO") // Solo expertos pueden crear
                        .requestMatchers(HttpMethod.PUT, "/api/participantes/editar/**").hasAuthority("ROLE_EXPERTO") // Solo expertos pueden editar
                        .requestMatchers(HttpMethod.GET, "/api/especialidades/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/evaluaciones/todas").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/evaluaciones/**").hasAuthority("ROLE_EXPERTO")
                        .requestMatchers(HttpMethod.GET, "/api/especialidades/todos").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/participantes/crear").hasAuthority("ROLE_EXPERTO")
                        .requestMatchers(HttpMethod.GET, "/api/pruebas/todas").hasAuthority("ROLE_EXPERTO")
                        .requestMatchers(HttpMethod.POST, "/api/pruebas/crear-con-pdf").hasAuthority("ROLE_EXPERTO")
                        .requestMatchers(HttpMethod.POST, "/api/items/crear-multiples").hasAuthority("ROLE_EXPERTO")





//                        .requestMatchers(HttpMethod.GET, "/api/especialidades/**").permitAll() // Permitir acceso público para listar especialidades
                        .requestMatchers(HttpMethod.GET, "/api/expertos/**").permitAll() // Permitir acceso público
                        .requestMatchers(HttpMethod.GET, "/api/ganadores/**").permitAll() // Permitir acceso público
//                        .requestMatchers(HttpMethod.POST, "/api/especialidades").hasRole("ADMIN") // Solo ADMIN puede crear especialidades
//                        .requestMatchers(HttpMethod.PUT, "/api/especialidades/**").hasRole("ADMIN") // Solo ADMIN puede editar especialidades
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll() // Permitir registro sin autenticación
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll() // Permitir acceso público a autenticación
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() // Permitir login sin autenticación
                        .anyRequest().authenticated()); // Requiere autenticación para cualquier otra petición

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Añadir filtro JWT para validación

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Origen permitido
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos HTTP permitidos
        configuration.setAllowedHeaders(Arrays.asList("*")); // Permitir cualquier cabecera
        configuration.setAllowCredentials(true); // Permitir credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Registrar configuración CORS
        return source;
    }
}

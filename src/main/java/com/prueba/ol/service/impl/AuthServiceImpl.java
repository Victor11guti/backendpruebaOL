package com.prueba.ol.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.prueba.ol.DTO.AuthRequest;
import com.prueba.ol.DTO.AuthResponse;
import com.prueba.ol.entity.User;
import com.prueba.ol.repository.UserRepository;
import com.prueba.ol.security.JwtUtil;
import com.prueba.ol.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;


	@Override
	public AuthResponse login(AuthRequest request) {
		  try {
	            // Autenticar usuario
	            authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                    request.getEmail(),
	                    request.getPassword()
	                )
	            );

	            // Buscar usuario en BD
	            User user = userRepository.findByEmail(request.getEmail())
	                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	            // Generar token con el email como subject
	            String token = jwtUtil.generateToken(user.getEmail());

	            return new AuthResponse(token);
	        } catch (BadCredentialsException e) {
	            throw new RuntimeException("Credenciales inválidas");
	        } catch (Exception e) {
	            throw new RuntimeException("Error durante el login: " + e.getMessage());
	        }
	}
}

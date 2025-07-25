package com.prueba.ol.service;

import com.prueba.ol.DTO.AuthRequest;
import com.prueba.ol.DTO.AuthResponse;

public interface AuthService {
	 AuthResponse login(AuthRequest request);
}

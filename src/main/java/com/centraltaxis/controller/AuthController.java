package com.centraltaxis.controller;

import com.centraltaxis.dto.auth.JwtResponse;
import com.centraltaxis.dto.auth.LoginRequest;
import com.centraltaxis.dto.auth.MessageResponse;
import com.centraltaxis.dto.auth.SignupRequest;
import com.centraltaxis.model.Usuario;
import com.centraltaxis.repository.UsuarioRepository;
import com.centraltaxis.security.JwtUtils;
import com.centraltaxis.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (usuarioRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: ¡El nombre de usuario ya está en uso!"));
        }

        // Crear nuevo usuario
        Usuario user = new Usuario(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));

        usuarioRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("¡Usuario registrado exitosamente!"));
    }
}
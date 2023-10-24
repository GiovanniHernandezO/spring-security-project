package com.cursos.springsecutirycourse.controller;

import com.cursos.springsecutirycourse.dto.AuthenticationRequestDTO;
import com.cursos.springsecutirycourse.dto.AuthenticationResponseDTO;
import com.cursos.springsecutirycourse.entity.User;
import com.cursos.springsecutirycourse.repository.UserRepository;
import com.cursos.springsecutirycourse.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

     @Autowired
     private AuthenticationService authenticationService;

     @Autowired
     private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(
            @RequestBody @Valid AuthenticationRequestDTO authenticationRequestDTO) {
        System.out.println("entrando");
        List<User> usuarios = userRepository.findAll();
        if (usuarios != null && !usuarios.isEmpty()) {
            for (User u : usuarios) {
                System.out.println("usuarios: " + u.getUsername());
            }
        } else {
            System.out.println("no hay usuarios");
        }


        AuthenticationResponseDTO jwtDTO = authenticationService.login(authenticationRequestDTO);
        return ResponseEntity.ok(jwtDTO);
    }

    @GetMapping("/public-access")
    public String publicAccesoEndpoint() {
        return "este endpoint es publico";
    }

}

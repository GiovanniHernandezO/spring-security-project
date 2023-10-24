package com.cursos.springsecutirycourse.service;

import com.cursos.springsecutirycourse.dto.AuthenticationRequestDTO;
import com.cursos.springsecutirycourse.dto.AuthenticationResponseDTO;
import com.cursos.springsecutirycourse.entity.User;
import com.cursos.springsecutirycourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public AuthenticationResponseDTO login(AuthenticationRequestDTO authenticationRequestDTO) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationRequestDTO.getUsername(),
                authenticationRequestDTO.getPassword()
        );
        authenticationManager.authenticate(authToken);

        User user = userRepository.findByUsername(authenticationRequestDTO.getUsername()).get();
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        return new AuthenticationResponseDTO(jwt);
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().name());
        return extraClaims;
    }
}

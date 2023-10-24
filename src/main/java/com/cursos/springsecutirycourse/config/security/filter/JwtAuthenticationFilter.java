package com.cursos.springsecutirycourse.config.security.filter;

import com.cursos.springsecutirycourse.entity.User;
import com.cursos.springsecutirycourse.repository.UserRepository;
import com.cursos.springsecutirycourse.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //1.- Obtener el header que contiene el JWT - formato Bearer JWT
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //2.- Obtener el JWT desde el header
        String jwt = authHeader.split(" ")[1];

        //3.- Obtener subject/username desde el JwtAuthenticationFilter
        String username = jwtService.extractUsername(jwt);

        //4.- Setear un objeto Authentication dentro del SecurityContext
        User user = userRepository.findByUsername(username).get();

        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        //5.- Ejecutar el resto de filtros
        filterChain.doFilter(request, response);
    }
}

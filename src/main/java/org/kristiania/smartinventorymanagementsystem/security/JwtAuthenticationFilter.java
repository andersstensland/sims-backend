package org.kristiania.smartinventorymanagementsystem.security;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.kristiania.smartinventorymanagementsystem.service.JwtService;
import org.kristiania.smartinventorymanagementsystem.service.UserDetailServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailServiceImpl userDetailsService; // we'll define below

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException, jakarta.servlet.ServletException {

        // 1. Check for Authorization header
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // No token, skip
        }

        // 2. Extract token
        final String jwt = authHeader.substring(7); // remove "Bearer "

        // 3. Validate and parse
        if (jwtService.validateToken(jwt)) {
            String username = jwtService.getUsernameFromToken(jwt);

            // 4. Load user details from DB
            var userDetails = userDetailsService.loadUserByUsername(username);
            // 5. Build authentication object
            var authorities = userDetails.getAuthorities();
            var authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, authorities
            );

            // 6. Set auth in context
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // continue with next filter
        filterChain.doFilter(request, response);
    }
}


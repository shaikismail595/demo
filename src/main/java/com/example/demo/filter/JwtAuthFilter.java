package com.example.demo.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.utils.JwtUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@SuppressWarnings("unchecked")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Retrieve the Authorization header
		String authHeader = request.getHeader("Authorization");
		
		String token = null;
	
		// Check if the header starts with "Bearer "
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7); // Extract token
		}
		if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Claims claims = jwtUtils.extractAllClaims(token);
            String username =claims.getSubject();
            
			List<String> roles = (List<String>) ((Map<String, Object>) ((Map<String, Object>) claims.get("https://127.0.0.1:3000/autorization")).get("authorization")).get("roles");
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
            
      
            // Create an authentication token
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set the authentication token in the security context
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
		// Continue the filter chain
		filterChain.doFilter(request, response);
	}
	
}

package com.example.blog.filter;

import com.example.blog.config.UserInfoUserDetailsService;
import com.example.blog.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Marks this class as a Spring component, so it is automatically detected by Spring's component scanning
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService; // Service responsible for handling JWT operations

    private UserInfoUserDetailsService userDetailsService; // Service responsible for loading user details based on username (email)

    /**
     * This method filters incoming HTTP requests to check for JWT authentication in the 'Authorization' header.
     * If a valid JWT is found, it sets the user details in the SecurityContext to authenticate the user.
     *
     * @param request  The incoming HTTP request
     * @param response The outgoing HTTP response
     * @param filterChain The filter chain to pass the request and response along
     * @throws ServletException If a servlet error occurs
     * @throws IOException If an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization"); // Retrieve the Authorization header from the request
        String token = null;
        String email = null;

        // Check if the Authorization header contains a Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Extract the token from the header
            email = jwtService.extractUsername(token); // Extract the username (email) from the token
        }

        // If email is extracted and there is no authentication set in the context
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details based on the extracted email
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Validate the token against the loaded user details
            if (jwtService.validateToken(token, userDetails)) {
                // If token is valid, create an authentication token for the user
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set additional details, such as the IP address of the request
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication token in the SecurityContext to mark the user as authenticated
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Pass the request and response to the next filter in the chain
        filterChain.doFilter(request, response);
    }
}

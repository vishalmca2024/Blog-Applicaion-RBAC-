package com.example.blog.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component // Marks this class as a Spring component, allowing it to be autowired into other classes
public class JwtService {

    // Secret key for signing the JWT token. It should be kept safe and not hardcoded in a production environment.
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    /**
     * Extracts the username (subject) from the JWT token.
     *
     * @param token The JWT token
     * @return The username (subject) extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Calls extractClaim with the Claims::getSubject function to extract the username
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token The JWT token
     * @return The expiration date of the token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Calls extractClaim with Claims::getExpiration to extract the expiration date
    }

    /**
     * A generic method to extract any claim from the JWT token.
     *
     * @param token The JWT token
     * @param claimsResolver A function to extract the desired claim (e.g., username, expiration)
     * @param <T> The type of the claim
     * @return The claim value of the specified type
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Extract all claims from the token
        return claimsResolver.apply(claims); // Apply the claims resolver function to get the desired claim
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token
     * @return The Claims object containing all claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey()) // Set the signing key to validate the token's signature
                .build()
                .parseClaimsJws(token) // Parse the JWT and get the claims
                .getBody(); // Return the body (claims) of the parsed JWT
    }

    /**
     * Checks if the token is expired.
     *
     * @param token The JWT token
     * @return True if the token is expired, false otherwise
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Compare token's expiration date with the current date
    }

    /**
     * Validates the JWT token by checking if the username matches and the token is not expired.
     *
     * @param token The JWT token
     * @param userDetails The user details to match against
     * @return True if the token is valid, false otherwise
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // Extract username from the token
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Validate the token by checking the username and expiration
    }

    /**
     * Generates a JWT token for the given username.
     *
     * @param userName The username for which the token is generated
     * @return The generated JWT token
     */
    public String generateToken(String userName) {
        Map<String,Object> claims = new HashMap<>(); // A map to store claims, which are custom attributes (can be empty for basic token)
        return createToken(claims, userName); // Create and return the JWT token
    }

    /**
     * Creates a JWT token with the specified claims and username.
     *
     * @param claims The claims to be included in the token
     * @param userName The username for the subject of the token
     * @return The generated JWT token
     */
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims) // Set the claims in the token
                .setSubject(userName) // Set the subject (username) of the token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set the issue date as the current time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // Set the expiration time (30 minutes from the current time)
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Sign the token using the secret key and HMAC SHA-256 algorithm
                .compact(); // Build and return the compact JWT token
    }

    /**
     * Gets the signing key used to sign and validate the JWT token.
     *
     * @return The signing key
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET); // Decode the base64 encoded secret key
        return Keys.hmacShaKeyFor(keyBytes); // Return the HMAC SHA key for signing the JWT
    }
}

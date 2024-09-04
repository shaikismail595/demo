package com.example.demo.utils;

import java.io.ByteArrayInputStream;
import java.security.Key;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import okhttp3.OkHttpClient;
import okhttp3.Request;
@Component
public class JwtUtils {
	public static final String AUTH0_DOMAIN = "dev-7000.us.auth0.com";
	public static final String SECRET = "MIIEpAIBAAKCAQEAn1pMVSEDO4EPzQxKgAakFxRgMGiewWZFAktenWo5aMt/OIso";


	// Generate token with given user name
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

	// Create a JWT token with specified claims and subject (user name)
	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // Token valid for 30 minutes
				.signWith(getSignKey(), SignatureAlgorithm.RS256).compact();
	}

	// Get the signing key for JWT token
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Extract the username from the token
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// Extract the expiration date from the token
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	// Extract a claim from the token
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// Extract all claims from the token
	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(token).getBody();
	}
	 
	// Check if the token is expired
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Validate the token against user details and expiration
	public Boolean validateToken(String token, UserDetails userDetails) {
		
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	
	private Key getPublicKey() {
        String publicKeyUrl = "https://" + AUTH0_DOMAIN + "/.well-known/jwks.json";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(publicKeyUrl).build();
        try {
            okhttp3.Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
            String publicKeyPEM = jsonNode.get("keys").get(0).get("x5c").get(0).asText();
            publicKeyPEM = publicKeyPEM.replace("-----BEGIN CERTIFICATE-----", "");
            publicKeyPEM = publicKeyPEM.replace("-----END CERTIFICATE-----", "");
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPEM);
            X509Certificate certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(publicKeyBytes));
            return (RSAPublicKey) certificate.getPublicKey();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch public key", e);
        }
        
	}

	@SuppressWarnings("unchecked")
	public List<String> extractAllRoles(Claims claims) {
		List<String> roles = (List<String>) ((Map<String, Object>) ((Map<String, Object>) claims.get("https://127.0.0.1:3000/autorization")).get("authorization")).get("roles");
		return roles;
	}

	public boolean isTokenExpired(Claims claims) {
	    long currentTime = System.currentTimeMillis(); 
	    long expirationTime = claims.getExpiration().getTime();

	    return currentTime >= expirationTime; 
	}
}

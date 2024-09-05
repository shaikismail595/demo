package com.example.demo.utils;

import java.io.ByteArrayInputStream;
import java.security.Key;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import okhttp3.OkHttpClient;
import okhttp3.Request;


@Component
public class JwtUtils {
	public static final String AUTH0_DOMAIN = "dev-qtv5dgs8uks1pc4x.us.auth0.com";

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(token).getBody();
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
			X509Certificate certificate = (X509Certificate) CertificateFactory.getInstance("X.509")
					.generateCertificate(new ByteArrayInputStream(publicKeyBytes));
			return (RSAPublicKey) certificate.getPublicKey();
		} catch (Exception e) {
			throw new RuntimeException("Failed to fetch public key", e);
		}

	}

	@SuppressWarnings("unchecked")
	public List<String> extractAllRoles(Claims claims) {
		List<String> roles = (List<String>) ((Map<String, Object>) ((Map<String, Object>) claims
				.get("https://127.0.0.1:3000/autorization")).get("authorization")).get("roles");
		return roles;
	}

	public boolean isTokenExpired(Claims claims) {
		long currentTime = System.currentTimeMillis();
		long expirationTime = claims.getExpiration().getTime();

		return currentTime >= expirationTime;
	}
}

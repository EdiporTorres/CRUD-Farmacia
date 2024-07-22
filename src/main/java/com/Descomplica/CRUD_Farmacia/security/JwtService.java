package com.Descomplica.CRUD_Farmacia.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	public static final String SECRET = "84c8ff74004a5de98cd4818935e1796518399d9ed8152da6923eefa3ed608d9f";
	
	private SecretKey getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
		}

		public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
			final Claims claims = extractAllClaims(token);
			return claimsResolver.apply(claims);
		}
		
		public String extractUsername(String token) {
			return extractClaim(token, Claims::getSubject);
		}

		public Date extractExpiration(String token) {
			return extractClaim(token, Claims::getExpiration);
		}
		
		private boolean isTokenExpired(String token) {
			return extractExpiration(token).before(new Date());
		}
		
		public boolean validateToken(String token, UserDetails userDetails) {
			final String username = extractUsername(token);
			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		}
		
		private String createToken(Map<String, Object> claims, String userName) {
			return Jwts.builder().setClaims(claims).setSubject(userName)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)).signWith(getSignKey())
					.compact();
		}

		public String generateToken(String username) {
			Map<String, Object> claims = new HashMap<>();
			return createToken(claims, username);
		}
}

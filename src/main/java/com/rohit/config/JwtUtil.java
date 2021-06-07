package com.rohit.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtUtil {

	private String secret;
	private int jwtExpirationInMs;

	@Value("${jwt.secret}")
	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Value("${jwt.expirationDateInMs}")
	public void setJwtExpirationInMs(int jwtExpirationInMs) {
		this.jwtExpirationInMs = jwtExpirationInMs;
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();

		Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();

		if (roles.contains(new SimpleGrantedAuthority("ROLE_POLICE"))) {
			claims.put("isPolice", true);
		}
		if (roles.contains(new SimpleGrantedAuthority("ROLE_COMMON"))) {
			claims.put("isCommon", true);
		}
		if (roles.contains(new SimpleGrantedAuthority("ROLE_CENTRAL"))) {
			claims.put("isCentral", true);
		}
		if (roles.contains(new SimpleGrantedAuthority("ROLE_AMBULANCE"))) {
			claims.put("isAmbulance", true);
		}
		if (roles.contains(new SimpleGrantedAuthority("ROLE_MEDICAL"))) {
			claims.put("isMedical", true);
		}

		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
				.signWith(SignatureAlgorithm.HS512, secret).compact();

	}

	public boolean validateToken(String authToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
		} catch (ExpiredJwtException ex) {
			throw ex;
		}
	}

	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return claims.getSubject();

	}

	public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

		List<SimpleGrantedAuthority> roles = null;

		Boolean isPolice = claims.get("isPolice", Boolean.class);
		Boolean isCommon = claims.get("isCommon", Boolean.class);
		Boolean isCentral = claims.get("isCentral", Boolean.class);
		Boolean isAmbulance = claims.get("isAmbulance", Boolean.class);
		Boolean isMedical = claims.get("isMedical", Boolean.class);

		if (isPolice != null) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_POLICE"));
		}

		else if (isCommon != null) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_COMMON"));
		} else if (isCentral != null) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_CENTRAL"));
		} else if (isAmbulance != null) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_AMBULANCE"));
		} else if (isMedical != null) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_MEDICAL"));
		}
		return roles;

	}

}

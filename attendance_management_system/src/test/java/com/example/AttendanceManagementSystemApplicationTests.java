package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

// @SpringBootTest
class AttendanceManagementSystemApplicationTests {

	@Test
	public void testUUID() {
		for (int i = 0; i < 10; i++) {
			System.out.println(UUID.randomUUID().toString());
		}
	}

	@Test
	public void testGetJWT() {
		Map<String, Object> claims = new HashMap<>();
		claims.put("Id", 1);
		claims.put("Name", "KaiShen");
		SecretKey key = Jwts.SIG.HS256.key().build();
		String jwt = Jwts.builder().claim("name", "KaiShen").claim("passwd", "123456").signWith(key).compact();
		System.out.println(jwt);
		Jws<Claims> jws = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt);
		System.out.println(jws.getPayload().get("name"));
		System.out.println(jws.getPayload().get("passwd"));
	}
}

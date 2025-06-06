package com.example.utils;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtils {
        private static String key = "6dS/9RAjpBhZ+fe8W5I/leEPaWs7KABlV5Mkgt1ekS4=";

        public static String generateToken(int id, Short role) {
                String jws = Jwts.builder()
                                .claim("id", id)
                                .claim("role", role)
                                .expiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                                .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(key)))
                                .compact();
                return jws;
        }

        public static Map<String, Integer> parseToken(String token) {
                Map<String, Integer> map = new HashMap<>();
                Jws<Claims> jws = Jwts.parser()
                                .verifyWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(key)))
                                .build()
                                .parseSignedClaims(token);
                map.put("id", Integer.parseInt(jws.getPayload().get("id").toString()));
                map.put("role", Integer.parseInt(jws.getPayload().get("role").toString()));
                return map;
        }
}

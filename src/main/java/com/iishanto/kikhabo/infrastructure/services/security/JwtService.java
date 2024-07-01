package com.iishanto.kikhabo.infrastructure.services.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private final long jwtExpiration;
    private final SecretKey secretKey;

    public JwtService(@Value("${kikhabo.security.jwt-secret}") String secret,@Value("${kikhabo.security.jwt-expire-in-days}") double expire){
        this.jwtExpiration=(long) expire*24*60*60*1000;//in mils
        this.secretKey=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication){
        String username=authentication.getName();
        Date today=new Date();
        Date expireDate=new Date(today.getTime()+jwtExpiration);
        return Jwts.builder().
                setSubject(username).
                setIssuer("JWT Service").
                setIssuedAt(today).
                setExpiration(expireDate).
                signWith(secretKey)
                .compact();
    }

    public String getUserEmailFromToken(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }
}
